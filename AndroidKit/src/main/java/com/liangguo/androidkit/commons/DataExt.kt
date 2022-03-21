package com.liangguo.androidkit.commons

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow


/**
 * @author ldh
 * 时间: 2022/1/27 17:00
 * 邮箱: 2637614077@qq.com
 */

/**
 * 用这种方法可以避免LiveData在设置相同的值时依旧通知
 */
inline var <T> MutableLiveData<T?>.smartNotifyValue: T?
    get() = this.value
    set(value) = kotlin.run {
        if (this.value != value) {
            this.value = value
        }
    }

/**
 * 设置值，如果与当前已有的值不一样则会进行更新，并执行[ifNotify]函数。
 */
fun <T> MutableLiveData<T>.setValueSmartNotify(value: T, ifNotify: ((T) -> Unit)? = null) {
    if (this.value != value) {
        this.value = value
        ifNotify?.invoke(value)
    }
}

/**
 * 用这种方法可以避免LMutableStateFlow在设置相同的值时依旧通知
 */
inline var <T>  MutableStateFlow<T>.smartNotifyValue: T
    get() = this.value
    set(value) = kotlin.run {
        if (this.value != value) {
            this.value = value
        }
    }

/**
 * 设置值，如果与当前已有的值不一样则会进行更新，并执行[ifNotify]函数。
 */
fun <T> MutableStateF low<T>.setValueSmartNotify(value: T, ifNotify: ((T) -> Unit)? = null) {
    if (this.value != value) {
        this.value = value
        ifNotify?.invoke(value)
    }
}