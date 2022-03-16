package com.liangguo.androidkit.app

import com.liangguo.easyingcontext.EasyingContext.context
import java.io.File


/**
 * @author ldh
 * 时间: 2022/2/20 11:36
 * 邮箱: 2637614077@qq.com
 */

/**
 * 计算应用缓存大小
 */
fun getAppCacheSize(): Long {
    var cacheSize = context.cacheDir.totalLength
    cacheSize += context.externalCacheDir?.totalLength ?: 0
    return cacheSize
}

/**
 * 删除应用缓存
 */
fun clearAppCache() {
    context.cacheDir.deleteFileOrDir()
    context.externalCacheDir?.deleteFileOrDir()
}


/**
 * 将文件大小转换为对应的单位
 * @return String类型的文件大小
 */
fun formatStorageSize(size: Long): String {
    val kb = size / 1024f
    if (kb < 1) {
        return "${(size / 1024).toInt()}B"
    }
    val mb = kb / 1024f
    if (mb < 1) {
        return String.format("%.2f", kb) + "KB"
    }
    val gb = mb / 1024f
    if (gb < 1) {
        return String.format("%.2f", mb) + "MB"
    }
    return String.format("%.2f", gb) + "GB"
}

/**
 * 删除一个文件
 * 如果是文件夹，则先递归删除其下的所有文件，再删除它本身
 */
fun File.deleteFileOrDir(): Boolean {
    if (isDirectory) {
        listFiles()?.let {
            for (f in it) {
                f.deleteFileOrDir()
            }
        }
    }
    return delete()
}


/**
 * 获取文件后缀
 * 如果没有后缀就是null
 */
val File.suffix: String?
    get() = if (name.contains('.'))
        name.substring(name.lastIndexOf(".") + 1)
    else null

/**
 * 获取文件大小，并且支持获取文件夹的总大小
 */
val File.totalLength: Long
    get() = if (isFile) length()
    else {
        var sum = 0L
        listFiles()?.forEach {
            sum += it.totalLength
        }
        sum
    }