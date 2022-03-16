package com.liangguo.androidkit.color

import android.R
import android.content.Context
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import com.liangguo.androidkit.color.ColorUtil.isColorLight


/**
 * @author ldh
 * 时间: 2022/2/5 13:44
 * 邮箱: 2637614077@qq.com
 */


/**
 * 获取当前主题的颜色属性
 * @param attr 属性的id，比如 R.attr.colorPrimary
 */
fun Context.resolveColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

/**
 * 背景是否是暗色，可以用来检测是两主题还是暗主题
 */
fun Context.isWindowBackgroundDark(): Boolean {
    return !isColorLight(resolveColor(R.attr.windowBackground))
}

val Context.colorPrimary: Int get() = resolveColor(R.attr.colorPrimary)

val Context.colorPrimaryDark: Int get() = resolveColor(R.attr.colorPrimaryDark)

val Context.colorAccent: Int get() = resolveColor(R.attr.colorAccent)

val Context.colorSecondary: Int
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    get() = resolveColor(R.attr.colorSecondary)