package com.liangguo.androidkit.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity


/**
 * @author ldh
 * 时间: 2023/2/20 20:19
 * 邮箱: ldh.liangguo@outlook.com
 */
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
 */
private fun getDeviceIdByHardware(context: Context): String? {
    val deviceId = StringBuilder()
    // 渠道标志
    deviceId.append("a")
    try {
        //wifi mac地址
        val wifi = context.getSystemService(ComponentActivity.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        val wifiMac = info.macAddress
        if (!wifiMac.isNullOrEmpty()) {
            deviceId.append("wifi")
            deviceId.append(wifiMac)
            return deviceId.toString()
        }
        //IMEI（imei）
        val tm = context.getSystemService(ComponentActivity.TELEPHONY_SERVICE) as TelephonyManager
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
