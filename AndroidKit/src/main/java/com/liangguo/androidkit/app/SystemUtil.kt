package com.liangguo.androidkit.app

import android.os.Build
import java.util.*


/**
 * @author ldh
 * 时间: 2022/3/18 16:13
 * 邮箱: 2637614077@qq.com
 */
object SystemUtil {
    /**
     * 获取当前手机系统语言。
     *
     * 例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    val systemLanguage: String
        get() = Locale.getDefault().language

    /**
     * 获取当前系统上的语言列表(Locale列表)
     */
    val systemLanguageList: Array<Locale?>?
        get() = Locale.getAvailableLocales()


    /**
     * 获取当前手机系统版本号
     */
    val systemVersion: String
        get() = Build.VERSION.RELEASE


    /**
     * 获取手机型号
     */
    val systemModel: String
        get() = Build.MODEL


    /**
     * 获取手机厂商
     */
    val deviceBrand: String
        get() = Build.BRAND


}