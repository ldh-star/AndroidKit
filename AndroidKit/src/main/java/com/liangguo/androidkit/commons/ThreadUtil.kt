package com.liangguo.androidkit.commons

import android.os.Build
import android.os.Handler
import android.os.Looper


/**
 * @author ldh
 * 时间: 2022/2/21 11:12
 * 邮箱: 2637614077@qq.com
 */

/**
 * 主线程上的Handler
 */
val MainExecutor = Handler(Looper.getMainLooper())

/**
 * 在主线程中运行一段代码，如果已经在主线程了，就立即执行，否则就post到主线程执行
 */
fun runInMainThread(block: () -> Unit) {
    if (isMainThread) block()
    else MainExecutor.post(block)
}

/**
 * 在主线程中运行一段代码，如果已经在主线程了，就立即执行，否则就post到主线程执行
 */
fun runInMainThread(runnable: Runnable) = runInMainThread { runnable.run() }


/**
 * 检查当前是否在主线程
 */
val isMainThread: Boolean
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Looper.getMainLooper().isCurrentThread
    } else {
        Looper.getMainLooper().thread == Thread.currentThread()
    }