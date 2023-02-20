package com.liangguo.androidkit.commons

import android.graphics.Bitmap
import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer


/**
 * @author ldh
 * 时间: 2023/2/20 20:38
 * 邮箱: ldh.liangguo@outlook.com
 */
object MediaUtils {


    /**
     * 将图片转换成Base64编码的字符串
     */
    fun imageToBase64(path: String): String? {
        if (!File(path).exists()) {
            return null
        }
        var inputStream: InputStream? = null
        val data: ByteArray
        var result: String? = null
        try {
            inputStream = FileInputStream(path)
            //创建一个字符流大小的数组。
            data = ByteArray(inputStream.available())
            //写入数组
            inputStream.read(data)
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return result
    }

}

/**
 * 获取Bitmap的字节流
 */
val Bitmap.bytes: ByteArray
    get() = run {
        val buffer = ByteBuffer.allocate(byteCount)
        copyPixelsToBuffer(buffer)
        buffer.array()
    }
