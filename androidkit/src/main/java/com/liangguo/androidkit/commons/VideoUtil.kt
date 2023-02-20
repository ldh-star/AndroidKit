package com.liangguo.androidkit.commons

import android.media.MediaMetadataRetriever


/**
 * @author ldh
 * 时间: 2022/2/14 10:47
 * 邮箱: 2637614077@qq.com
 */

/**
 * 获取视频的尺寸
 * @return 长度为2的数组，第一个是宽，第二个是高
 */
fun getVideoSize(filePath: String): IntArray {
    val size = IntArray(2)
    try {
        val mmr = MediaMetadataRetriever()
        size[0] =
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull()
                ?: 0
        size[1] =
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull()
                ?: 0
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return size
}

/**
 * 获取视频时长
 */
fun getVideoDuration(videoPath: String): Long {
    var duration = 0L
    try {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(videoPath)
        duration =
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull()
                ?: 0
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return duration
}
