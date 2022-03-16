package com.liangguo.androidkit.commons

import android.content.Intent
import com.liangguo.easyingcontext.EasyingContext


/**
 * @author ldh
 * 时间: 2022/3/1 13:28
 * 邮箱: 2637614077@qq.com
 */

/**
 * 模拟按下Home键的事件
 */
fun backToHome() {
    val home = Intent(Intent.ACTION_MAIN)
    home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    home.addCategory(Intent.CATEGORY_HOME)
    home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    EasyingContext.context.startActivity(home)
}
