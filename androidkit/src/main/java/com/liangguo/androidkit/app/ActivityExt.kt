package com.liangguo.androidkit.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.liangguo.easyingcontext.EasyingContext
import java.io.BufferedReader
import java.io.InputStreamReader
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
    context: Context = EasyingContext.currentActivity ?: EasyingContext.context,
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
    context: Context = EasyingContext.currentActivity ?: EasyingContext.context,
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


/**
 * 隐藏软键盘
 */
fun Activity.hideSoftInputFromWindow() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
    currentFocus?.let {
        if (inputMethodManager is InputMethodManager) {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

/**
 * 获取安卓屏幕的尺寸等数据，包括屏幕尺寸、像素、像素点密度
 */
fun Activity.getAndroidScreenProperty(): List<Pair<String, String>> {
    val list = mutableListOf<Pair<String, String>>()
    val dm = DisplayMetrics()
    display?.getMetrics(dm) ?: windowManager.defaultDisplay.getMetrics(dm)
    val width: Int = dm.widthPixels // 屏幕宽度（像素）
    val height: Int = dm.heightPixels // 屏幕高度（像素）
    val density: Float = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
    val densityDpi: Int = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）
    // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
    val screenWidth = (width / density).toInt() // 屏幕宽度(dp)
    val screenHeight = (height / density).toInt() // 屏幕高度(dp)

    list.add("屏幕宽度（像素）" to width.toString())
    list.add("屏幕高度（像素）" to height.toString())
    list.add("屏幕密度（0.75 / 1.0 / 1.5）" to density.toString())
    list.add("屏幕密度dpi（120 / 160 / 240）" to densityDpi.toString())
    list.add("屏幕密度dpi（120 / 160 / 240）" to densityDpi.toString())
    list.add("屏幕宽度（dp）" to screenWidth.toString())
    list.add("屏幕高度（dp）" to screenHeight.toString())

    return list
}


/**
 * 模拟按下Home键跳到桌面
 */
fun Context.backToHome() {
    val home = Intent(Intent.ACTION_MAIN)
    home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    home.addCategory(Intent.CATEGORY_HOME)
    home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(home)
}

/**
 * 从assets的文件读取字符串
 * 比如： readStringFromAssets("config/AppConfig.json")
 */
fun Context.readStringFromAssets(fileName: String): String? {
    try {
        val inputReader = InputStreamReader(resources.assets.open(fileName))
        val bufReader = BufferedReader(inputReader)
        var line: String?
        var result = ""
        while (bufReader.readLine().also { line = it } != null) result += line
        return result
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}