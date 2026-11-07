package com.example.igsessions

object Presets {
    private const val C = "131.0.6778.135"
    private const val IG_VER = "422.1.0.44.67"

    // Every iPhone ever created (Name to Apple Identifier)
    private val iphones = listOf(
        // Foldable iPhone Duo & iPhone 18 Series
        "iPhone Duo" to "iPhone19,4", "iPhone Duo (7.6\" Unfolded)" to "iPhone19,4", "iPhone Duo (5.4\" Folded)" to "iPhone19,4",
        "iPhone 18 Pro Max" to "iPhone19,2", "iPhone 18 Pro" to "iPhone19,1", "iPhone 18" to "iPhone19,3",
        "iPhone 18e" to "iPhone19,6", "iPhone Air 2" to "iPhone19,4",
        // iPhone 17 Series
        "iPhone 17 Pro Max" to "iPhone18,2", "iPhone 17 Pro" to "iPhone18,1", "iPhone 17 Air" to "iPhone18,4",
        "iPhone 17 Plus" to "iPhone18,4", "iPhone 17" to "iPhone18,3",
        // iPhone 16 Series
        "iPhone 16 Pro Max" to "iPhone17,2", "iPhone 16 Pro" to "iPhone17,1", "iPhone 16 Plus" to "iPhone17,4",
        "iPhone 16" to "iPhone17,3", "iPhone 16e" to "iPhone17,5",
        // iPhone 15 Series
        "iPhone 15 Pro Max" to "iPhone16,2", "iPhone 15 Pro" to "iPhone16,1", "iPhone 15 Plus" to "iPhone15,5", "iPhone 15" to "iPhone15,4",
        // iPhone 14 Series
        "iPhone 14 Pro Max" to "iPhone15,3", "iPhone 14 Pro" to "iPhone15,2", "iPhone 14 Plus" to "iPhone14,8", "iPhone 14" to "iPhone14,7",
        // iPhone 13 Series
        "iPhone 13 Pro Max" to "iPhone14,3", "iPhone 13 Pro" to "iPhone14,2", "iPhone 13" to "iPhone14,5", "iPhone 13 mini" to "iPhone14,4",
        // iPhone 12 Series
        "iPhone 12 Pro Max" to "iPhone13,4", "iPhone 12 Pro" to "iPhone13,3", "iPhone 12" to "iPhone13,2", "iPhone 12 mini" to "iPhone13,1",
        // iPhone 11 Series
        "iPhone 11 Pro Max" to "iPhone12,5", "iPhone 11 Pro" to "iPhone12,3", "iPhone 11" to "iPhone12,1",
        // iPhone X / XS / XR Series
        "iPhone XS Max" to "iPhone11,6", "iPhone XS" to "iPhone11,2", "iPhone XR" to "iPhone11,8", "iPhone X" to "iPhone10,6",
        // iPhone SE Series
        "iPhone SE (3rd Gen)" to "iPhone14,6", "iPhone SE (2nd Gen)" to "iPhone12,8", "iPhone SE (1st Gen)" to "iPhone8,4",
        // iPhone 8 / 7 / 6 / 5 / 4 / 3G / Original
        "iPhone 8 Plus" to "iPhone10,5", "iPhone 8" to "iPhone10,4",
        "iPhone 7 Plus" to "iPhone9,4", "iPhone 7" to "iPhone9,3",
        "iPhone 6s Plus" to "iPhone8,2", "iPhone 6s" to "iPhone8,1",
        "iPhone 6 Plus" to "iPhone7,1", "iPhone 6" to "iPhone7,2",
        "iPhone 5s" to "iPhone6,2", "iPhone 5c" to "iPhone5,4", "iPhone 5" to "iPhone5,2",
        "iPhone 4S" to "iPhone4,1", "iPhone 4" to "iPhone3,1", "iPhone 3GS" to "iPhone2,1", "iPhone 3G" to "iPhone1,2", "iPhone (Original)" to "iPhone1,1"
    )

    // Every iPad family (Name to Apple Identifier)
    private val ipads = listOf(
        "iPad Pro 13\" (M4)" to "iPad16,6", "iPad Pro 11\" (M4)" to "iPad16,4",
        "iPad Pro 12.9\" (M2)" to "iPad14,6", "iPad Pro 11\" (M2)" to "iPad14,4",
        "iPad Pro 12.9\" (M1)" to "iPad13,10", "iPad Pro 11\" (M1)" to "iPad13,5",
        "iPad Pro 12.9\" (4th Gen)" to "iPad8,12", "iPad Pro 11\" (2nd Gen)" to "iPad8,10",
        "iPad Pro 10.5\"" to "iPad7,4", "iPad Pro 9.7\"" to "iPad6,4",
        "iPad Air 13\" (M2)" to "iPad14,11", "iPad Air 11\" (M2)" to "iPad14,9",
        "iPad Air (5th Gen M1)" to "iPad13,17", "iPad Air (4th Gen)" to "iPad13,2", "iPad Air (3rd Gen)" to "iPad11,4", "iPad Air 2" to "iPad5,4",
        "iPad mini (A17 Pro)" to "iPad16,2", "iPad mini (6th Gen)" to "iPad14,2", "iPad mini (5th Gen)" to "iPad11,2", "iPad mini 4" to "iPad5,2",
        "iPad (10th Gen)" to "iPad13,18", "iPad (9th Gen)" to "iPad12,2", "iPad (8th Gen)" to "iPad11,7", "iPad (7th Gen)" to "iPad7,12", "iPad (6th Gen)" to "iPad7,6"
    )

    // Every Foldable & Dual-Screen Android Device (Name, Brand, Model Code)
    private val foldables = listOf(
        Triple("Microsoft Surface Duo 2", "Microsoft", "Surface Duo 2"),
        Triple("Microsoft Surface Duo", "Microsoft", "Surface Duo"),
        Triple("Google Pixel 9 Pro Fold", "Google", "Pixel 9 Pro Fold"),
        Triple("Google Pixel Fold", "Google", "Pixel Fold"),
        Triple("Galaxy Z Fold6", "samsung", "SM-F956B"), Triple("Galaxy Z Fold5", "samsung", "SM-F946B"),
        Triple("Galaxy Z Fold4", "samsung", "SM-F936B"), Triple("Galaxy Z Fold3", "samsung", "SM-F926B"),
        Triple("Galaxy Z Fold2", "samsung", "SM-F916B"), Triple("Galaxy Fold", "samsung", "SM-F900F"),
        Triple("Galaxy Z Flip6", "samsung", "SM-F741B"), Triple("Galaxy Z Flip5", "samsung", "SM-F731B"),
        Triple("Galaxy Z Flip4", "samsung", "SM-F721B"), Triple("Galaxy Z Flip3", "samsung", "SM-F711B"),
        Triple("Galaxy Z Flip", "samsung", "SM-F700F"),
        Triple("OnePlus Open", "OnePlus", "CPH2551"),
        Triple("Huawei Mate XT Ultimate (Tri-Fold)", "HUAWEI", "GRL-AL10"),
        Triple("Huawei Mate X6", "HUAWEI", "ICL-AL10"), Triple("Huawei Mate X5", "HUAWEI", "ALT-AL10"),
        Triple("Honor Magic V3", "HONOR", "FCP-N49"), Triple("Honor Magic V2", "HONOR", "VER-N49"),
        Triple("Xiaomi Mix Fold 4", "Xiaomi", "24072PX77C"), Triple("Xiaomi Mix Fold 3", "Xiaomi", "2308CPXD0C"),
        Triple("Xiaomi Mix Flip", "Xiaomi", "2405CPX3DG"),
        Triple("Vivo X Fold3 Pro", "vivo", "V2330"), Triple("Oppo Find N3", "OPPO", "CPH2499"),
        Triple("Oppo Find N3 Flip", "OPPO", "CPH2519"),
        Triple("Motorola Razr+ (2024)", "motorola", "motorola razr plus 2024"),
        Triple("Motorola Razr Ultra 50", "motorola", "XT2451-3"),
        Triple("Motorola Razr 40 Ultra", "motorola", "XT2321-1")
    )

    // Every major Android Phone & Tablet (Name, Brand, Model Code)
    private val androids = listOf(
        // Google Pixel & Nexus
        Triple("Pixel 10 Pro XL", "Google", "Pixel 10 Pro XL"), Triple("Pixel 10 Pro", "Google", "Pixel 10 Pro"), Triple("Pixel 10", "Google", "Pixel 10"),
        Triple("Pixel 9 Pro XL", "Google", "Pixel 9 Pro XL"), Triple("Pixel 9 Pro", "Google", "Pixel 9 Pro"), Triple("Pixel 9", "Google", "Pixel 9"), Triple("Pixel 9a", "Google", "Pixel 9a"),
        Triple("Pixel 8 Pro", "Google", "Pixel 8 Pro"), Triple("Pixel 8", "Google", "Pixel 8"), Triple("Pixel 8a", "Google", "Pixel 8a"),
        Triple("Pixel 7 Pro", "Google", "Pixel 7 Pro"), Triple("Pixel 7", "Google", "Pixel 7"), Triple("Pixel 7a", "Google", "Pixel 7a"),
        Triple("Pixel 6 Pro", "Google", "Pixel 6 Pro"), Triple("Pixel 6", "Google", "Pixel 6"), Triple("Pixel 6a", "Google", "Pixel 6a"),
        Triple("Pixel 5", "Google", "Pixel 5"), Triple("Pixel 4a 5G", "Google", "Pixel 4a (5G)"), Triple("Pixel 4 XL", "Google", "Pixel 4 XL"),
        Triple("Pixel 3 XL", "Google", "Pixel 3 XL"), Triple("Pixel 2 XL", "Google", "Pixel 2 XL"), Triple("Pixel XL", "Google", "Pixel XL"),
        Triple("Pixel Tablet", "Google", "Pixel Tablet"), Triple("Nexus 6P", "Google", "Nexus 6P"), Triple("Nexus 5X", "Google", "Nexus 5X"),

        // Samsung Galaxy S Series (S25 down to S3)
        Triple("Galaxy S25 Ultra", "samsung", "SM-S938B"), Triple("Galaxy S25+", "samsung", "SM-S936B"), Triple("Galaxy S25", "samsung", "SM-S931B"),
        Triple("Galaxy S24 Ultra", "samsung", "SM-S928B"), Triple("Galaxy S24+", "samsung", "SM-S926B"), Triple("Galaxy S24", "samsung", "SM-S921B"), Triple("Galaxy S24 FE", "samsung", "SM-S721B"),
        Triple("Galaxy S23 Ultra", "samsung", "SM-S918B"), Triple("Galaxy S23+", "samsung", "SM-S916B"), Triple("Galaxy S23", "samsung", "SM-S911B"), Triple("Galaxy S23 FE", "samsung", "SM-S711B"),
        Triple("Galaxy S22 Ultra", "samsung", "SM-S908B"), Triple("Galaxy S22+", "samsung", "SM-S906B"), Triple("Galaxy S22", "samsung", "SM-S901B"),
        Triple("Galaxy S21 Ultra", "samsung", "SM-G998B"), Triple("Galaxy S21", "samsung", "SM-G991B"), Triple("Galaxy S21 FE", "samsung", "SM-G990B"),
        Triple("Galaxy S20 Ultra", "samsung", "SM-G988B"), Triple("Galaxy S20", "samsung", "SM-G981B"), Triple("Galaxy S20 FE", "samsung", "SM-G780G"),
        Triple("Galaxy S10+", "samsung", "SM-G975F"), Triple("Galaxy S10", "samsung", "SM-G973F"), Triple("Galaxy S10e", "samsung", "SM-G970F"),
        Triple("Galaxy S9+", "samsung", "SM-G965F"), Triple("Galaxy S8+", "samsung", "SM-G955F"), Triple("Galaxy S7 Edge", "samsung", "SM-G935F"),

        // Samsung Galaxy Note, A, M & Tab Series
        Triple("Galaxy Note 20 Ultra", "samsung", "SM-N986B"), Triple("Galaxy Note 20", "samsung", "SM-N981B"),
        Triple("Galaxy Note 10+", "samsung", "SM-N975F"), Triple("Galaxy Note 9", "samsung", "SM-N960F"),
        Triple("Galaxy A55 5G", "samsung", "SM-A556E"), Triple("Galaxy A54 5G", "samsung", "SM-A546B"), Triple("Galaxy A53 5G", "samsung", "SM-A536B"),
        Triple("Galaxy A35 5G", "samsung", "SM-A356E"), Triple("Galaxy A34 5G", "samsung", "SM-A346B"), Triple("Galaxy A15 5G", "samsung", "SM-A156B"),
        Triple("Galaxy M55 5G", "samsung", "SM-M556B"), Triple("Galaxy M35 5G", "samsung", "SM-M356B"),
        Triple("Galaxy Tab S10 Ultra", "samsung", "SM-X920"), Triple("Galaxy Tab S9 Ultra", "samsung", "SM-X910"), Triple("Galaxy Tab S9", "samsung", "SM-X710"),

        // OnePlus
        Triple("OnePlus 13", "OnePlus", "PJZ110"), Triple("OnePlus 12", "OnePlus", "CPH2573"), Triple("OnePlus 12R", "OnePlus", "CPH2609"),
        Triple("OnePlus 11", "OnePlus", "CPH2449"), Triple("OnePlus 10 Pro", "OnePlus", "NE2213"), Triple("OnePlus 9 Pro", "OnePlus", "LE2123"),
        Triple("OnePlus 8 Pro", "OnePlus", "IN2023"), Triple("OnePlus 7 Pro", "OnePlus", "GM1913"), Triple("OnePlus Nord 4", "OnePlus", "CPH2663"),

        // Xiaomi, Redmi & POCO
        Triple("Xiaomi 15 Pro", "Xiaomi", "2410DPN6CC"), Triple("Xiaomi 15", "Xiaomi", "24129PN74C"),
        Triple("Xiaomi 14 Ultra", "Xiaomi", "24030PN60G"), Triple("Xiaomi 14", "Xiaomi", "23127PN0CG"),
        Triple("Xiaomi 13 Pro", "Xiaomi", "2210132G"), Triple("Xiaomi 12 Pro", "Xiaomi", "2201122G"),
        Triple("Redmi Note 14 Pro+", "Xiaomi", "24090RA29G"), Triple("Redmi Note 13 Pro+", "Xiaomi", "23090RA98G"), Triple("Redmi Note 12 Pro", "Xiaomi", "22101316G"),
        Triple("Redmi K80 Pro", "Xiaomi", "24117RK2CC"), Triple("Redmi K70 Ultra", "Xiaomi", "2407FRK8EC"),
        Triple("POCO F6 Pro", "POCO", "23113RKC6G"), Triple("POCO F6", "POCO", "24069PC21G"), Triple("POCO X6 Pro", "POCO", "2311DRK48G"), Triple("POCO F5", "POCO", "23049PCD8G"),

        // Nothing, Sony, ASUS, Motorola
        Triple("Nothing Phone (2a) Plus", "Nothing", "A142P"), Triple("Nothing Phone (2a)", "Nothing", "A142"),
        Triple("Nothing Phone (2)", "Nothing", "A065"), Triple("Nothing Phone (1)", "Nothing", "A063"),
        Triple("CMF Phone 1", "Nothing", "A015"),
        Triple("Sony Xperia 1 VI", "Sony", "XQ-EC54"), Triple("Sony Xperia 1 V", "Sony", "XQ-DQ72"), Triple("Sony Xperia 5 V", "Sony", "XQ-DE54"),
        Triple("ASUS ROG Phone 9 Pro", "asus", "ASUS_AI2501"), Triple("ASUS ROG Phone 8 Pro", "asus", "ASUS_AI2401_D"), Triple("ASUS Zenfone 11 Ultra", "asus", "ASUS_AI2401_H"),
        Triple("Motorola Edge 50 Ultra", "motorola", "XT2401-1"), Triple("Motorola Edge+ (2023)", "motorola", "XT2301-4"), Triple("Moto G85 5G", "motorola", "XT2427-2"),

        // Vivo, Oppo, Realme, iQOO, Honor, Huawei
        Triple("Vivo X200 Pro", "vivo", "V2405A"), Triple("Vivo X100 Ultra", "vivo", "V2366GA"), Triple("Vivo X100 Pro", "vivo", "V2324A"),
        Triple("iQOO 13", "vivo", "V2408A"), Triple("iQOO 12 Pro", "vivo", "V2329A"),
        Triple("Oppo Find X8 Pro", "OPPO", "PKC110"), Triple("Oppo Find X7 Ultra", "OPPO", "PHY110"), Triple("Oppo Reno 12 Pro", "OPPO", "CPH2629"),
        Triple("Realme GT 7 Pro", "realme", "RMX5010"), Triple("Realme GT 6", "realme", "RMX3851"), Triple("Realme 13 Pro+", "realme", "RMX3921"),
        Triple("Honor Magic 7 Pro", "HONOR", "PTP-AN10"), Triple("Honor Magic 6 Pro", "HONOR", "BVL-N49"),
        Triple("Huawei Pura 70 Ultra", "HUAWEI", "HBP-LX9"), Triple("Huawei Mate 60 Pro", "HUAWEI", "ALN-AL00"),

        // Classic Legends (LG, HTC, BlackBerry)
        Triple("LG Wing 5G", "LGE", "LM-F100N"), Triple("LG V60 ThinQ", "LGE", "LM-V600"),
        Triple("HTC U24 Pro", "HTC", "2QDA100"), Triple("BlackBerry Key2", "BlackBerry", "BBF100-6")
    )

    private val iVers = listOf("27_2", "27_1", "27_0", "18_2", "17_6", "16_7")
    private val aVers = listOf("16", "15", "14", "13")

    val all: List<Device> by lazy {
        buildList {
            // 1. iPhones & iPhone Duo (Using your exact verified format)
            for ((idx, pair) in iphones.withIndex()) {
                val (name, code) = pair
                for (v in iVers) {
                    val dv = v.replace('_', '.')
                    val category = if (name.contains("Duo")) "Fold & Duo" else "iPhone"
                    add(
                        Device(
                            id = "ip_${idx}_$v",
                            name = "$name · iOS $dv",
                            category = category,
                            ua = "Mozilla/5.0 (iPhone; CPU iPhone OS $v like Mac OS X) " +
                                    "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                                    "Version/$dv Mobile/15E148 Safari/604.1 " +
                                    "Instagram $IG_VER " +
                                    "($code; iOS $v; en_US; en-US; " +
                                    "scale=3.00; 1320x2868; IABMV/1; 918355888)"
                        )
                    )
                }
            }

            // 2. iPads
            for ((idx, pair) in ipads.withIndex()) {
                val (name, code) = pair
                for (v in listOf("27_2", "27_0", "18_2", "17_6")) {
                    val dv = v.replace('_', '.')
                    add(
                        Device(
                            id = "ipad_${idx}_$v",
                            name = "$name · iPadOS $dv",
                            category = "iPad",
                            ua = "Mozilla/5.0 (iPad; CPU OS $v like Mac OS X) " +
                                    "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                                    "Version/$dv Mobile/15E148 Safari/604.1 " +
                                    "Instagram $IG_VER " +
                                    "($code; iOS $v; en_US; en-US; " +
                                    "scale=2.00; 2064x2752; IABMV/1; 918355888)"
                        )
                    )
                }
            }

            // 3. Foldables & Surface Duo
            for ((idx, triple) in foldables.withIndex()) {
                val (name, brand, model) = triple
                for (v in aVers) {
                    add(
                        Device(
                            id = "fold_${idx}_$v",
                            name = "$name · Android $v",
                            category = "Fold & Duo",
                            ua = "Mozilla/5.0 (Linux; Android $v; $model Build/AP3A.241005.015; wv) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Version/4.0 Chrome/$C Mobile Safari/537.36 " +
                                    "Instagram $IG_VER Android (35/$v; 480dpi; 1840x2208; $brand; $model; $model; qcom; en_US; 918355888; IABMV/1)"
                        )
                    )
                }
            }

            // 4. Android Phones & Tablets
            for ((idx, triple) in androids.withIndex()) {
                val (name, brand, model) = triple
                for (v in aVers) {
                    add(
                        Device(
                            id = "and_${idx}_$v",
                            name = "$name · Android $v",
                            category = "Android",
                            ua = "Mozilla/5.0 (Linux; Android $v; $model Build/AP3A.241005.015; wv) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Version/4.0 Chrome/$C Mobile Safari/537.36 " +
                                    "Instagram $IG_VER Android (35/$v; 480dpi; 1440x3120; $brand; $model; $model; qcom; en_US; 918355888; IABMV/1)"
                        )
                    )
                }
            }

            // 5. Desktop: Windows
            val win10 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$C Safari/537.36"
            add(Device("w11_c", "Windows 11 · Chrome", "Windows", win10))
            add(Device("w11_e", "Windows 11 · Edge", "Windows", "$win10 Edg/$C"))
            add(Device("w11_o", "Windows 11 · Opera GX", "Windows", "$win10 OPR/115.0.0.0"))
            add(Device("w11_f", "Windows 11 · Firefox", "Windows", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:133.0) Gecko/20100101 Firefox/133.0"))
            add(Device("w8_c", "Windows 8.1 · Chrome", "Windows", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36"))
            add(Device("w7_c", "Windows 7 · Chrome", "Windows", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36"))

            // 6. Desktop: macOS
            add(Device("m_m4_s", "MacBook Pro (M4 Max) · Safari 18", "macOS", "Mozilla/5.0 (Macintosh; Intel Mac OS X 15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.1 Safari/605.1.15"))
            add(Device("m_m3_c", "MacBook Air (M3) · Chrome", "macOS", "Mozilla/5.0 (Macintosh; Intel Mac OS X 15_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$C Safari/537.36"))
            add(Device("m_m2_f", "Mac Studio (M2 Ultra) · Firefox", "macOS", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14.7; rv:133.0) Gecko/20100101 Firefox/133.0"))
            add(Device("m_imac", "iMac 24\" (M3) · Safari", "macOS", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15"))

            // 7. Desktop & Handheld: Linux & ChromeOS
            add(Device("l_c", "Linux (x86_64) · Chrome", "Linux", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$C Safari/537.36"))
            add(Device("l_u", "Ubuntu 24.04 · Firefox", "Linux", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0"))
            add(Device("l_fe", "Fedora · Firefox", "Linux", "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0"))
            add(Device("l_ar", "Arch Linux · Chrome", "Linux", "Mozilla/5.0 (X11; Arch Linux; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$C Safari/537.36"))
            add(Device("l_ka", "Kali Linux · Firefox", "Linux", "Mozilla/5.0 (X11; Kali; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0"))
            add(Device("l_sd", "Steam Deck (SteamOS) · Chrome", "Linux", "Mozilla/5.0 (X11; Linux x86_64; Valve Steam GamePad) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$C Safari/537.36"))
            add(Device("l_cr", "Chromebook (ChromeOS) · Chrome", "Linux", "Mozilla/5.0 (X11; CrOS x86_64 16002.51.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$C Safari/537.36"))
        }
    }
}