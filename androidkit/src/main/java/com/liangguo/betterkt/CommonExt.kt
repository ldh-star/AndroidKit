package com.liangguo.betterkt


/**
 * @author ldh
 * 时间: 2023/2/20 19:52
 * 邮箱: ldh.liangguo@outlook.com
 */
/**
 * 对于任何实例，试着把他们转换为另一种类的实例并在函数块中执行相应的操作。
 */
inline fun <reified T> Any?.tryAs(func: (T) -> Unit) {
    if (this is T) {
        func(this)
    }
}

/**
 * 尝试转换对象的类型，若不能转换成功则返回null
 */
inline fun <reified T> Any?.tryAsOrNull(): T? {
    if (this is T) {
        return this
    }
    return null
}
