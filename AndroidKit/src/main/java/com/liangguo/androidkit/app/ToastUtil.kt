package com.liangguo.androidkit.app

import android.widget.Toast
import com.liangguo.easyingcontext.EasyingContext.context
import es.dmoral.toasty.Toasty

/**
 * @author ldh
 * 时间: 2021/10/26 16:04
 * 邮箱: 2637614077@qq.com
 */
object ToastUtil {

    var DEFAULT_DURATION = Toast.LENGTH_SHORT

    fun toast(message: Any, duration: Int = DEFAULT_DURATION) {
        Toast.makeText(context, message.toString(), duration).show()
    }

    fun success(message: Any, duration: Int = DEFAULT_DURATION) {
        Toasty.success(context, message.toString(), duration).show()
    }

    fun error(message: Any, duration: Int = DEFAULT_DURATION) {
        Toasty.error(context, message.toString(), duration).show()
    }

    fun warning(message: Any, duration: Int = DEFAULT_DURATION) {
        Toasty.warning(context, message.toString(), duration).show()
    }

    fun info(message: Any, duration: Int = DEFAULT_DURATION) {
        Toasty.info(context, message.toString(), duration).show()
    }

}
