package com.liangguo.androidkit.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.liangguo.easyingcontext.EasyingContext
import kotlin.reflect.KClass


/**
 * @author ldh
 * 时间: 2022/1/21 15:04
 * 邮箱: 2637614077@qq.com
 */

/**
 * 切割边界的转场动画。
 * 加了个动画而已，还是调用的是[startNewActivity]。
 *
 * @param sourceView 从哪个View开始进行切割动画(动画初始的矩形将由这个View来决定)
 */
fun Class<out Activity>.startActivityWithClipRevealAnimation(
    context: Context,
    intent: Intent = Intent(context, this).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) },
    sourceView: View,
    configIntent: Intent.() -> Unit = {}
) {
    val options = ActivityOptionsCompat.makeClipRevealAnimation(
        sourceView,
        0,
        0,
        sourceView.width,
        sourceView.height
    )
    startNewActivity(
        context = context,
        intent = intent,
        bundle = options.toBundle(),
        configIntent = configIntent
    )
}

/**
 * 通过context和Activity的Class启动一个新Activity。
 *
 * - 注意：在这里Intent会默认加入[Intent.FLAG_ACTIVITY_NEW_TASK]的属性，如果不要此属性，可以自定义Intent传进来，也可以在函数块中手动移除。
 *
 * @param intent 默认有一个，也可以自己设。
 * @param bundle 默认为空。
 * @param configIntent 在这个函数块里可以进行对Intent配置。
 */
fun Class<out Activity>.startNewActivity(
    context: Context,
    intent: Intent = Intent(context, this).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) },
    bundle: Bundle? = null,
    configIntent: Intent.() -> Unit = {}
) {
    configIntent(intent)
    context.startActivity(intent, bundle)
}

/**
 * 通过context和Activity的Class启动一个新Activity。
 *
 * - 注意：在这里Intent会默认加入[Intent.FLAG_ACTIVITY_NEW_TASK]的属性，如果不要此属性，可以自定义Intent传进来，也可以在函数块中手动移除。
 *
 * @param intent 默认有一个，也可以自己设。
 * @param bundle 默认为空。
 * @param configIntent 在这个函数块里可以进行对Intent配置。
 */
fun KClass<out Activity>.startNewActivity(
    context: Context = EasyingContext.context,
    intent: Intent = Intent(context, this.java).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) },
    bundle: Bundle? = null,
    configIntent: Intent.() -> Unit = {}
) = java.startNewActivity(context, intent, bundle, configIntent)

/**
 * 调用Activity中的setContentView，并对设置的这个View进行配置。
 * @param view 要添加进ContentView的View
 * @param viewConfig 在这个函数块中可以给View做配置
 */
fun <V : View> Activity.setContentView(view: V, viewConfig: V.() -> Unit = {}) {
    setContentView(view)
    view.viewConfig()
}

/**
 * 为Lifecycle添加监听。
 *
 * @return LifecycleEventObserver的实体。
 */
fun Lifecycle.observe(block: (Lifecycle.Event) -> Unit) =
    LifecycleEventObserver { _, event -> block(event) }.also { addObserver(it) }

/**
 * Activity的ContentView，通过android.R.id.content来找到，一般是个FrameLayout。
 */
val Activity.contentView: ViewGroup?
    get() = findViewById(android.R.id.content)


/**
 * 隐藏状态栏和导航栏
 * 谷歌官网给出的解决办法，但是全是过时的方法，也看不到改用什么新的方法，将就用了
 */
fun Activity.hideSystemUI() {
    // Enables regular immersive mode.
    // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
    // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}

/**
 * 显示状态栏和导航栏
 * 谷歌官网给出的解决办法，但是全是过时的方法，也看不到改用什么新的方法，将就用了
 */
fun Activity.showSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}
