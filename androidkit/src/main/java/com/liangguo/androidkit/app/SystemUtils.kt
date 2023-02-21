package com.liangguo.androidkit.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import com.liangguo.betterkt.tryAsOrNull
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader


/**
 * @author ldh
 * 时间: 2023/2/20 20:19
 * 邮箱: ldh.liangguo@outlook.com
 */
object SystemUtils {

    /**
     * 通过shell命令来获取设备序列号，需要root
     */
    fun getSerialNumber(): String? {
        var serial: String? = null
        try {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java)
            serial = get.invoke(c, "ro.serialno").tryAsOrNull<String>()?.trim()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return serial
    }


    /**
     * 通过shell命令来获取设备物理 WLAN mac地址，需要root
     */
    fun getWLANMacByShell(): String? {
        try {
            val pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)
            return input.readText().trim()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}

/**
 * 当前应用环境连接的网络
 */
val Context.wifiNetwork: Network?
    get() {
        val connectMan = getSystemService(ConnectivityManager::class.java)
        val foundNetworks = connectMan.allNetworks
        for (foundNet: Network in foundNetworks) {
            val netCaps: NetworkCapabilities = connectMan.getNetworkCapabilities(foundNet)!!
            if (netCaps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return foundNet
            }
        }
        return null
    }

/**
 * 通过硬件获取设备唯一标识
 *
 * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
 *
 * 渠道标志为：
 * 1，andriod（a）
 *
 * 识别符来源标志：
 * 1， wifi mac地址（wifi）；
 * 2， IMEI（imei）；
 * 3， 序列号（sn）；
 * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
 */
fun Context.getDeviceIdByHardware(): String? {
    val deviceId = StringBuilder()
    // 渠道标志
    deviceId.append("a")
    try {
        //wifi mac地址
        val wifi = getSystemService(ComponentActivity.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        val wifiMac = info.macAddress
        if (!wifiMac.isNullOrEmpty()) {
            deviceId.append("wifi")
            deviceId.append(wifiMac)
            return deviceId.toString()
        }
        //IMEI（imei）
        val tm = getSystemService(ComponentActivity.TELEPHONY_SERVICE) as TelephonyManager
        val imei = tm.deviceId
        if (!imei.isNullOrEmpty()) {
            deviceId.append("imei")
            deviceId.append(imei)
            return deviceId.toString()
        }
        //序列号（sn）
        val sn = tm.simSerialNumber
        if (!sn.isNullOrEmpty()) {
            deviceId.append("sn")
            deviceId.append(sn)
            return deviceId.toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
