package com.hdteam.appquality.taq.utils.util

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.DisplayMetrics
import com.hdteam.appquality.taq.di.ProviderInstance
import com.hdteam.appquality.taq.model.InfoDevice
import com.hdteam.appquality.taq.utils.extension.getTypeNetworkConnect
import com.hdteam.appquality.taq.utils.extension.isInternetAvailable
import java.io.File
import java.util.Locale
import java.util.TimeZone


object InfoDevice {

    fun getAllInfoAppHtml(): String {

        val htmlUtils = HtmlUtils()
        htmlUtils.addTitleColSpan("Info Device", 2)
        htmlUtils.addRowTitle(listOf("Field", "Value"))
        getAllInfo().forEach { info ->
            htmlUtils.addRow(listOf(info.name, info.value))
        }
        return htmlUtils.returnTableHtml().toString()
    }

    fun getAllInfo(): MutableList<InfoDevice> {
        val (availableStorage, totalStorage) = getStorageInfo()
        val (width, height) = getScreenSize()

        val listDeviceInfo = mutableListOf<InfoDevice>()

        listDeviceInfo.addAll(getInfoVersionApp())
        listDeviceInfo.add(InfoDevice(name = "Device MODEL", value = Build.MODEL))
        listDeviceInfo.add(InfoDevice(name = "Device BRAND", value = Build.BRAND))
        listDeviceInfo.add(
            InfoDevice(
                name = "Device Build.VERSION.RELEASE",
                value = "${Build.VERSION.RELEASE}"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device MANUFACTURER",
                value = "${Build.MANUFACTURER}"
            )
        )
        listDeviceInfo.add(InfoDevice(name = "Device PRODUCT", value = "${Build.PRODUCT}"))
        listDeviceInfo.add(
            InfoDevice(
                name = "Device SUPPORTED_32_BIT_ABIS",
                value = "${Build.SUPPORTED_32_BIT_ABIS.map { it.toString() }}"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device SUPPORTED_64_BIT_ABIS",
                value = "${Build.SUPPORTED_64_BIT_ABIS.map { it.toString() }}"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device SUPPORTED_ABIS",
                value = "${Build.SUPPORTED_ABIS.map { it.toString() }}"
            )
        )
        listDeviceInfo.add(InfoDevice(name = "Device HOST", value = "${Build.HOST}"))
        listDeviceInfo.add(InfoDevice(name = "Device DISPLAY", value = "${Build.DISPLAY}"))

        listDeviceInfo.add(
            InfoDevice(
                name = "Device Display metric",
                value = "${getDisplayMetrics()}"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device Screen size: with x height ",
                value = "${width} x ${height}"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device Available Storage",
                value = "${FormatUtils.formatBytes(availableStorage)}, ($availableStorage)"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device Total Storage",
                value = "${FormatUtils.formatBytes(totalStorage)} ($totalStorage)"
            )
        )
        val (availableRam, totalRam) = getRamInfo()
        listDeviceInfo.add(
            InfoDevice(
                name = "Device Available Ram",
                value = "${FormatUtils.formatBytes(availableRam)}, ($availableRam)"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device Total Ram",
                value = "${FormatUtils.formatBytes(totalRam)}, ($totalRam)"
            )
        )
        listDeviceInfo.add(InfoDevice(name = "Device TimeZone", value = "${getTimeZone()}"))
        listDeviceInfo.add(InfoDevice(name = "Device Locale", value = "${Locale.getDefault()}"))
        listDeviceInfo.add(
            InfoDevice(
                name = "Device has network",
                value = "${isInternetAvailable()}"
            )
        )
        listDeviceInfo.add(
            InfoDevice(
                name = "Device type network",
                value = getTypeNetworkConnect() ?: "No detection"
            )
        )


        listDeviceInfo.add(InfoDevice(name = "Device is Root", value = "${isRooted()}"))
        listDeviceInfo.add(InfoDevice(name = "Device is Emulator", value = "${checkIsEmulator()}"))
        listDeviceInfo.add(
            InfoDevice(
                name = "Created report at",
                value = "${FormatUtils.formatTime(System.currentTimeMillis())}"
            )
        )

        return listDeviceInfo
    }

    fun getRamInfo(): Pair<Long, Long> {
        val activityManager = ProviderInstance.activityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val availableRam = memoryInfo.availMem
        val totalRam = memoryInfo.totalMem

        return Pair(availableRam, totalRam)
    }

    fun getRamInfoFormat(): String {
        val (availableRam, totalRam) = getRamInfo()
        return "${FormatUtils.formatBytes(availableRam)}/${FormatUtils.formatBytes(totalRam)}"
    }

    fun getStorageInfo(): Pair<Long, Long> {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        val totalBlocks = stat.blockCountLong

        val availableStorage = availableBlocks * blockSize
        val totalStorage = totalBlocks * blockSize

        return Pair(availableStorage, totalStorage)
    }


    fun getDisplayMetrics(): String {
        return when (Resources.getSystem().displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "LDPI"
            DisplayMetrics.DENSITY_MEDIUM -> "MDPI"
            DisplayMetrics.DENSITY_HIGH -> "HDPI"
            DisplayMetrics.DENSITY_XHIGH -> "XHDPI"
            DisplayMetrics.DENSITY_XXHIGH -> "XXHDPI"
            DisplayMetrics.DENSITY_XXXHIGH -> "XXXHDPI"
            else -> "HDPI"
        }
    }

    fun getTimeZone() = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)

    fun getScreenSize() = Pair(
        Resources.getSystem().displayMetrics.widthPixels,
        Resources.getSystem().displayMetrics.heightPixels
    )

    fun getVersionCode() = try {
        val pInfo = ProviderInstance.application.packageManager.getPackageInfo(
            ProviderInstance.application.packageName,
            0
        )
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            pInfo.longVersionCode
        } else {
            pInfo.versionCode.toLong()
        }
        versionCode
    } catch (e: Exception) {
        -1
    }

    fun getInfoVersionApp() = try {
        val pInfo = ProviderInstance.application.packageManager.getPackageInfo(
            ProviderInstance.application.packageName,
            0
        )
        val versionCode = getVersionCode()

        val listInfo = mutableListOf<InfoDevice>()
        listInfo.add(InfoDevice(name = "App Name", value = getApplicationName()))
        listInfo.add(
            InfoDevice(
                name = "Install first time",
                value = FormatUtils.formatTime(pInfo.firstInstallTime)
            )
        )
        listInfo.add(
            InfoDevice(
                name = "Install last time ",
                value = FormatUtils.formatTime(pInfo.lastUpdateTime)
            )
        )
        listInfo.add(InfoDevice(name = "Version code", value = versionCode.toString()))
        listInfo.add(InfoDevice(name = "Version name", value = pInfo.versionName.toString()))
        listInfo
    } catch (e: Exception) {
        emptyList<InfoDevice>()
    }

    fun checkIsEmulator(): Boolean {

        val result = (Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                && Build.FINGERPRINT.endsWith(":user/release-keys")
                && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone") && Build.BRAND == "google"
                && Build.MODEL.startsWith("sdk_gphone"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HOST == "Build2" //MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")

        return result
    }

    fun isRooted(): Boolean {
//     co the dung
        // CommonUtils.isEmulator(this)
        // co the dung CommonUtils.isRooted(this) cua firebase
        val isEmulator = checkIsEmulator()
        val buildTags = Build.TAGS
        if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
            return true
        } else {
            var file: File = File("/system/app/Superuser.apk")
            if (file.exists()) {
                return true
            } else {
                file = File("/system/xbin/su")
                return !isEmulator && file.exists()
            }
        }
    }


    fun getApplicationName(): String {
        return try {
            val applicationInfo = ProviderInstance.application.applicationInfo
            val stringId = applicationInfo.labelRes
            if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else ProviderInstance.application.getString(
                stringId
            )
        } catch (e: Exception) {
            "Unknown"
        }
    }

}