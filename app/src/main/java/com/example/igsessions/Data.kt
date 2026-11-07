package com.example.igsessions

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable data class Device(val id: String, val name: String, val category: String, val ua: String, val custom: Boolean = false)
@Serializable data class Session(
    val id: String,
    val name: String,
    val deviceName: String,
    val ua: String,
    val keepAlive: Boolean = false
)

private val Context.store by preferencesDataStore("ig")
private val json = Json { ignoreUnknownKeys = true }
private val SESS = stringPreferencesKey("sessions")
private val DEV = stringPreferencesKey("devices")
private val ACT = stringPreferencesKey("active")
private val FX = floatPreferencesKey("fx")
private val FY = floatPreferencesKey("fy")

class AppState(private val ctx: Context, private val scope: CoroutineScope) {
    val sessions = mutableStateListOf<Session>()
    val custom = mutableStateListOf<Device>()
    var activeId by mutableStateOf<String?>(null)
    var fab by mutableStateOf(Offset(-1f, -1f))
    var loaded by mutableStateOf(false)
    val active get() = sessions.firstOrNull { it.id == activeId }

    suspend fun load() {
        val p = ctx.store.data.first()
        p[SESS]?.let { sessions.addAll(json.decodeFromString(ListSerializer(Session.serializer()), it)) }
        p[DEV]?.let { custom.addAll(json.decodeFromString(ListSerializer(Device.serializer()), it)) }
        activeId = p[ACT] ?: sessions.firstOrNull()?.id
        fab = Offset(p[FX] ?: -1f, p[FY] ?: -1f)
        loaded = true
    }

    fun save() {
        val s = json.encodeToString(ListSerializer(Session.serializer()), sessions.toList())
        val d = json.encodeToString(ListSerializer(Device.serializer()), custom.toList())
        val a = activeId; val f = fab
        scope.launch {
            ctx.store.edit {
                it[SESS] = s; it[DEV] = d; it[FX] = f.x; it[FY] = f.y
                if (a != null) it[ACT] = a else it.remove(ACT)
            }
        }
    }
}