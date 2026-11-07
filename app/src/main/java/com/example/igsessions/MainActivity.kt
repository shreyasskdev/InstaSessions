package com.example.igsessions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.webkit.ProfileStore
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) { App() }
        }
    }
}

// Custom WebView that stays unthrottled in the background and ignores touch when hidden
private class KeepAliveWebView(context: Context) : WebView(context) {
    var isForegroundSession: Boolean = true

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (isForegroundSession) super.dispatchTouchEvent(ev) else false
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        // Always report VISIBLE so Chromium never pauses timers/WebSockets for background sessions
        super.onWindowVisibilityChanged(View.VISIBLE)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val st = remember { AppState(ctx.applicationContext, scope) }
    LaunchedEffect(Unit) { st.load() }

    var sheet by remember { mutableStateOf(false) }
    var picker by remember { mutableStateOf(false) }
    var pending by remember { mutableStateOf<Device?>(null) }
    val webRef = remember { arrayOfNulls<WebView>(1) }

    var tick by remember { mutableIntStateOf(0) }
    var awake by remember { mutableStateOf(true) }
    LaunchedEffect(tick) { awake = true; delay(2500); awake = false }
    val alpha by animateFloatAsState(if (awake) 1f else 0.25f, label = "fab")

    BackHandler {
        val w = webRef[0]
        if (w?.canGoBack() == true) w.goBack() else (ctx as Activity).finish()
    }

    if (!st.loaded) return
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // Render the active session PLUS any session with the Play (keepAlive) button enabled
        val runningSessions = st.sessions.filter { it.id == st.activeId || it.keepAlive }

        if (st.active == null && runningSessions.isEmpty()) {
            Column(Modifier.fillMaxSize().systemBarsPadding(), Arrangement.Center, Alignment.CenterHorizontally) {
                Text("No sessions yet", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                Button({ picker = true }) { Text("Create first session") }
            }
        } else {
            runningSessions.forEach { s ->
                key(s.id) {
                    val isCurrent = s.id == st.activeId
                    Box(
                        Modifier
                            .fillMaxSize()
                            .zIndex(if (isCurrent) 1f else 0f)
                            .alpha(if (isCurrent) 1f else 0f)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        SessionWeb(s, isCurrent, webRef, Modifier.fillMaxSize().systemBarsPadding())
                    }
                }
            }
        }

        // Draggable floating button (always on top with zIndex 2f)
        BoxWithConstraints(Modifier.fillMaxSize().zIndex(2f)) {
            val d = LocalDensity.current
            val size = with(d) { 52.dp.toPx() }
            val maxX = with(d) { maxWidth.toPx() } - size
            val maxY = with(d) { maxHeight.toPx() } - size
            val pos = if (st.fab.x < 0) Offset(maxX, maxY * 0.6f) else st.fab
            Surface(
                shape = CircleShape, color = MaterialTheme.colorScheme.primary, shadowElevation = 6.dp,
                modifier = Modifier.offset { IntOffset(pos.x.roundToInt(), pos.y.roundToInt()) }
                    .size(52.dp).alpha(alpha)
                    .pointerInput(Unit) {
                        detectDragGestures(onDragEnd = { st.save() }) { c, drag ->
                            c.consume(); tick++
                            val cur = if (st.fab.x < 0) pos else st.fab
                            st.fab = Offset((cur.x + drag.x).coerceIn(0f, maxX), (cur.y + drag.y).coerceIn(0f, maxY))
                        }
                    }
                    .clickable { tick++; sheet = true }
            ) { Box(contentAlignment = Alignment.Center) { Text("⇄", fontSize = 22.sp, color = MaterialTheme.colorScheme.onPrimary) } }
        }
    }

    if (sheet) ModalBottomSheet(onDismissRequest = { sheet = false }) {
        Column(Modifier.padding(horizontal = 16.dp).padding(bottom = 24.dp)) {
            Text("Sessions", style = MaterialTheme.typography.titleLarge)
            LazyColumn(Modifier.heightIn(max = 380.dp)) {
                items(st.sessions, key = { it.id }) { s ->
                    ListItem(
                        headlineContent = { Text(s.name) },
                        supportingContent = {
                            Text(s.deviceName + if (s.keepAlive) " · ● Running in background" else "")
                        },
                        leadingContent = { RadioButton(s.id == st.activeId, null) },
                        trailingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Play / Pause toggle button for keeping session alive in background
                                FilledTonalIconButton(
                                    onClick = {
                                        val idx = st.sessions.indexOfFirst { it.id == s.id }
                                        if (idx != -1) {
                                            st.sessions[idx] = s.copy(keepAlive = !s.keepAlive)
                                            st.save()
                                        }
                                    },
                                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                                        containerColor = if (s.keepAlive)
                                            MaterialTheme.colorScheme.primaryContainer
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Text(
                                        text = if (s.keepAlive) "⏸" else "▶",
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(Modifier.width(4.dp))
                                TextButton({
                                    val wasActive = s.id == st.activeId
                                    st.sessions.remove(s)
                                    if (wasActive) st.activeId = st.sessions.firstOrNull()?.id
                                    st.save()
                                    scope.launch {
                                        delay(800)
                                        try {
                                            if (WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE))
                                                ProfileStore.getInstance().deleteProfile("s${s.id}")
                                        } catch (_: Exception) {}
                                    }
                                }) { Text("Delete") }
                            }
                        },
                        modifier = Modifier.clickable { st.activeId = s.id; st.save(); sheet = false }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Button({ sheet = false; picker = true }, Modifier.fillMaxWidth()) { Text("＋ New session") }
        }
    }

    if (picker) DevicePicker(st.custom,
        onCreate = { st.custom.add(it); st.save() },
        onPick = { pending = it; picker = false },
        onDismiss = { picker = false })

    pending?.let { dev ->
        var name by remember { mutableStateOf("${dev.name} #${st.sessions.size + 1}") }
        AlertDialog(
            onDismissRequest = { pending = null },
            title = { Text("Session name") },
            text = { OutlinedTextField(name, { name = it }, singleLine = true) },
            confirmButton = {
                TextButton({
                    val s = Session(UUID.randomUUID().toString().replace("-", ""), name.ifBlank { dev.name }, dev.name, dev.ua)
                    st.sessions.add(s); st.activeId = s.id; st.save(); pending = null
                }) { Text("Create") }
            },
            dismissButton = { TextButton({ pending = null }) { Text("Cancel") } }
        )
    }
}

@Composable
fun SessionWeb(s: Session, isCurrent: Boolean, ref: Array<WebView?>, modifier: Modifier) {
    val activeUa = remember(s.id, s.deviceName, s.ua) {
        Presets.all.firstOrNull { it.name == s.deviceName }?.ua ?: s.ua
    }

    var fileCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val cb = fileCallback
        fileCallback = null
        if (cb == null) return@rememberLauncherForActivityResult
        val data = result.data
        val uris: Array<Uri>? = when {
            result.resultCode != Activity.RESULT_OK -> null
            data?.clipData != null -> {
                val clip = data.clipData!!
                Array(clip.itemCount) { i -> clip.getItemAt(i).uri }
            }
            data?.data != null -> arrayOf(data.data!!)
            else -> WebChromeClient.FileChooserParams.parseResult(result.resultCode, data)
        }
        cb.onReceiveValue(uris)
    }

    AndroidView(
        modifier = modifier,
        factory = { c ->
            KeepAliveWebView(c).apply {
                isForegroundSession = isCurrent
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                isVerticalScrollBarEnabled = false
                isHorizontalScrollBarEnabled = false
                overScrollMode = View.OVER_SCROLL_NEVER

                val cm = if (WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) {
                    val profile = ProfileStore.getInstance().getOrCreateProfile("s${s.id}")
                    WebViewCompat.setProfile(this, "s${s.id}")
                    profile.cookieManager
                } else {
                    Toast.makeText(c, "Update Android System WebView for isolated sessions", Toast.LENGTH_LONG).show()
                    CookieManager.getInstance()
                }
                cm.setAcceptCookie(true)
                cm.setAcceptThirdPartyCookies(this, true)

                val isDesktop = !activeUa.contains("Mobile", ignoreCase = true) &&
                        !activeUa.contains("Android", ignoreCase = true) &&
                        !activeUa.contains("iPhone", ignoreCase = true) &&
                        !activeUa.contains("iPad", ignoreCase = true)

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    allowFileAccess = true
                    allowContentAccess = true
                    mediaPlaybackRequiresUserGesture = false
                    javaScriptCanOpenWindowsAutomatically = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                    userAgentString = activeUa
                    useWideViewPort = true
                    loadWithOverviewMode = isDesktop
                    setSupportZoom(isDesktop)
                    builtInZoomControls = isDesktop
                    displayZoomControls = false
                }

                if (WebViewFeature.isFeatureSupported(WebViewFeature.DOCUMENT_START_SCRIPT)) {
                    val jsUa = activeUa.substringBefore(" Instagram ").replace("'", "\\'")
                    val platform = when {
                        activeUa.contains("iPhone") -> "iPhone"
                        activeUa.contains("iPad") -> "iPad"
                        activeUa.contains("Macintosh") -> "MacIntel"
                        activeUa.contains("Windows") -> "Win32"
                        activeUa.contains("Android") -> "Linux armv81"
                        else -> "Linux x86_64"
                    }
                    val isApple = activeUa.contains("iPhone") || activeUa.contains("iPad") ||
                            (activeUa.contains("Macintosh") && !activeUa.contains("Chrome"))
                    val vendor = when {
                        isApple -> "Apple Computer, Inc."
                        activeUa.contains("Firefox") -> ""
                        else -> "Google Inc."
                    }

                    val script = """
                        (function() {
                            try {
                                Object.defineProperty(navigator, 'userAgent', { get: () => '$jsUa' });
                                Object.defineProperty(navigator, 'platform', { get: () => '$platform' });
                                Object.defineProperty(navigator, 'vendor', { get: () => '$vendor' });
                                if ($isApple) {
                                    Object.defineProperty(navigator, 'userAgentData', { get: () => undefined });
                                }
                                // Keep page thinking it is always visible & focused for "Active now" status
                                Object.defineProperty(document, 'hidden', { get: () => false });
                                Object.defineProperty(document, 'visibilityState', { get: () => 'visible' });
                                document.hasFocus = () => true;
                                setInterval(() => {
                                    window.dispatchEvent(new Event('mousemove'));
                                    window.dispatchEvent(new Event('focus'));
                                }, 30000);
                            } catch (e) {}

                            const injectAppStyle = () => {
                                if (document.getElementById('ig-native-style')) return;
                                const style = document.createElement('style');
                                style.id = 'ig-native-style';
                                style.textContent = `
                                    * { -webkit-tap-highlight-color: transparent !important; }
                                    body, html { overscroll-behavior-y: none !important; }
                                    ::-webkit-scrollbar { display: none !important; width: 0 !important; height: 0 !important; }
                                `;
                                (document.head || document.documentElement).appendChild(style);
                            };
                            document.addEventListener('DOMContentLoaded', injectAppStyle);
                            injectAppStyle();
                        })();
                    """.trimIndent()
                    WebViewCompat.addDocumentStartJavaScript(this, script, setOf("*"))
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        fileCallback?.onReceiveValue(null)
                        fileCallback = filePathCallback
                        return try {
                            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "*/*"
                                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
                                putExtra(
                                    Intent.EXTRA_ALLOW_MULTIPLE,
                                    fileChooserParams?.mode == FileChooserParams.MODE_OPEN_MULTIPLE
                                )
                            }
                            filePickerLauncher.launch(Intent.createChooser(intent, "Select Photo or Video"))
                            true
                        } catch (_: Exception) {
                            fileCallback?.onReceiveValue(null)
                            fileCallback = null
                            false
                        }
                    }
                }

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                        val url = request.url.toString()
                        if (url.startsWith("http://") || url.startsWith("https://")) {
                            return false
                        }
                        if (url.startsWith("intent://")) {
                            try {
                                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                val fallback = intent.getStringExtra("browser_fallback_url")
                                if (!fallback.isNullOrBlank()) {
                                    view.loadUrl(fallback)
                                    return true
                                }
                            } catch (_: Exception) {}
                        }
                        return true
                    }

                    override fun onPageFinished(view: WebView, url: String?) {
                        super.onPageFinished(view, url)
                        cm.flush()
                    }
                }

                loadUrl("https://www.instagram.com/")
                if (isCurrent) ref[0] = this
            }
        },
        update = { webView ->
            webView.isForegroundSession = isCurrent
            if (isCurrent) {
                ref[0] = webView
            }
        },
        onRelease = { it.destroy(); if (ref[0] === it) ref[0] = null }
    )
}

@Composable
fun DevicePicker(custom: List<Device>, onCreate: (Device) -> Unit, onPick: (Device) -> Unit, onDismiss: () -> Unit) {
    var q by remember { mutableStateOf("") }
    var cat by remember { mutableStateOf("All") }
    var creating by remember { mutableStateOf(false) }
    val cats = listOf("All", "iPhone", "Fold & Duo", "iPad", "Android", "Windows", "macOS", "Linux", "Custom")
    val list = remember(q, cat, custom.size) {
        (custom + Presets.all).filter { d ->
            (cat == "All" || (cat == "Custom" && d.custom) || (cat == "iPhone" && d.name.startsWith("iPhone")) || d.category == cat) &&
                    q.split(" ").filter { it.isNotBlank() }.all { (d.name + " " + d.category + " " + d.ua).contains(it, true) }
        }
    }
    Dialog(onDismiss, DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.systemBarsPadding().padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(q, { q = it }, Modifier.weight(1f), label = { Text("Search ${list.size} devices") }, singleLine = true)
                    Spacer(Modifier.width(8.dp))
                    Button({ creating = true }) { Text("＋ New") }
                }
                Row(Modifier.horizontalScroll(rememberScrollState()).padding(vertical = 8.dp)) {
                    cats.forEach { FilterChip(cat == it, { cat = it }, { Text(it) }, Modifier.padding(end = 6.dp)) }
                }
                LazyColumn {
                    items(list, key = { it.id }) { d ->
                        ListItem(
                            overlineContent = { Text(d.category + if (d.custom) " · custom" else "") },
                            headlineContent = { Text(d.name) },
                            supportingContent = { Text(d.ua, maxLines = 2, fontSize = 11.sp) },
                            modifier = Modifier.clickable { onPick(d) }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
    if (creating) {
        var n by remember { mutableStateOf("") }
        var u by remember { mutableStateOf("") }
        var c by remember { mutableStateOf("iPhone") }
        AlertDialog(
            onDismissRequest = { creating = false },
            title = { Text("New device") },
            text = {
                Column {
                    OutlinedTextField(n, { n = it }, label = { Text("Name") }, singleLine = true)
                    OutlinedTextField(u, { u = it }, label = { Text("User-Agent string") }, minLines = 3)
                    Row(Modifier.horizontalScroll(rememberScrollState()).padding(top = 8.dp)) {
                        cats.drop(1).dropLast(1).forEach { FilterChip(c == it, { c = it }, { Text(it) }, Modifier.padding(end = 6.dp)) }
                    }
                }
            },
            confirmButton = {
                TextButton({
                    onCreate(Device("c_" + UUID.randomUUID(), n.trim(), c, u.trim(), custom = true)); creating = false
                }, enabled = n.isNotBlank() && u.isNotBlank()) { Text("Save") }
            },
            dismissButton = { TextButton({ creating = false }) { Text("Cancel") } }
        )
    }
}