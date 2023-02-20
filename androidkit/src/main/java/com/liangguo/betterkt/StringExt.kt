package com.liangguo.betterkt


/**
 * @author ldh
 * 时间: 2023/2/20 19:41
 * 邮箱: ldh.liangguo@outlook.com
 */

/**
 * 将数字转换成字符串并以0补位扩展，比如23变成“0023”
 *
 * @param digit 要补到几位数
 */
fun Int.digitString(digit: Int): String {
    var n = this
    val array = CharArray(digit) { '0' }
    var i = digit
    while (n > 0) {
        val t = n / 10
        array[--i] = (n - (t * 10)).digitToChar()
        n = t
    }
    return String(array)
}


/**
 * 分割出某个字符串的最后几位
 * 比如 2 -> “几位”
 *
 * 如果取的数字超出了原串长度，则直接返回null
 */
fun String?.subLastString(n: Int): String? =
    if (!isNullOrEmpty() && length > n) {
        substring(length - n)
    } else null


/**
 * 保留成几位小数的字符串
 */
fun Double.formatDecimal(n: Int) = "%.${n}f".format(this)


/**
 * 保留成几位小数的字符串
 */
fun Float.formatDecimal(n: Int) = "%.${n}f".format(this)
