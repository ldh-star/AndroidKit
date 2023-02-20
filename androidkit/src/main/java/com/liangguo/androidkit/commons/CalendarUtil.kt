package com.liangguo.androidkit.commons

import android.annotation.SuppressLint
import com.liangguo.androidkit.commons.CalendarUtil.toNearDayString
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * @author ldh
 * 时间: 13:03
 * 邮箱: 2637614077@qq.com
 */
object CalendarUtil {

    /**
     * 获取当前的时间
     */
    val now: Calendar
        get() = Calendar.getInstance()

    /**
     * 获取附近两天时间的字符串，如果不是最近两天，则返回null
     */
    fun Calendar.toNearDayString(): String? {
        val today = Calendar.getInstance()
        when {
            today.isSameDay(getNearDate(0)) -> {
                return "今天"
            }

            today.isSameDay(getNearDate(1)) -> {
                return "昨天"
            }

            today.isSameDay(getNearDate(2)) -> {
                return "前天"
            }

            today.isSameDay(getNearDate(-1)) -> {
                return "明天"
            }

            today.isSameDay(getNearDate(-2)) -> {
                return "后天"
            }

            else -> return null
        }
    }

    /**
     * calendar转UTC字符串
     * 如 2017-07-28T08:28:47.776Z
     */
    @SuppressLint("SimpleDateFormat")
    fun toUTC(calendar: Calendar): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(calendar.time)
    }

    /**
     * Calendar转完整时间字符串
     * 比如 2021年10月20日 星期天
     */
    fun toCompleteDateString(calendar: Calendar?): String {
        calendar?.let {
            return calendar[Calendar.YEAR].toString() + "年" + (calendar[Calendar.MONTH] + 1) + "月" + calendar[Calendar.DAY_OF_MONTH] + "日 " + calendar.dayOfWeek
        } ?: let {
            return toCompleteDateString(Calendar.getInstance())
        }
    }

    /**
     * 将时间字符串转化为Calendar对象
     * 比如  parseCalendar("yyyy-MM-dd HH:mm:ss", "1998-10-16 12:22:43")
     */
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun parseCalendar(pattern: String = "yyyy-MM-dd HH:mm:ss", date: String): Calendar {
        val format = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        try {
            calendar.time = format.parse(date)
        } catch (e: ParseException) {
            throw RuntimeException("日期转化错误  $e")
        }
        return calendar
    }

    /**
     * 获取时间差距
     *
     * @param calendarA
     * @param calendarB
     * @return
     */
    @JvmStatic
    fun getTimeDifference(calendarA: Calendar, calendarB: Calendar): String {
        var timeDifference = calendarA.timeInMillis - calendarB.timeInMillis
        if (timeDifference < 0) {
            timeDifference = -timeDifference
        }
        return formatDuring(timeDifference)
    }

    /**
     * 将一段毫秒转换成时间
     * @param millisTime 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    fun formatDuring(millisTime: Long): String {
        val days = millisTime / (1000 * 60 * 60 * 24)
        val hours = millisTime % (1000 * 60 * 60 * 24) / (1000 * 60 * 60)
        val minutes = millisTime % (1000 * 60 * 60) / (1000 * 60)
        val seconds = millisTime % (1000 * 60) / 1000
        return if (days > 0) {
            days.toString() + "天" + hours + "小时" + minutes + "分"
        } else if (hours > 0) {
            hours.toString() + "小时" + minutes + "分"
        } else if (minutes > 0) {
            minutes.toString() + "分" + seconds + "秒"
        } else if (seconds > 0) {
            seconds.toString() + "秒"
        } else {
            millisTime.toString() + "毫秒"
        }
    }

    /**
     * 获取某一天的起始时间
     *
     * @return
     */
    fun getStartOfDayTime(calendar: Calendar): Calendar {
        val date = calendar.clone() as Calendar
        date[Calendar.HOUR_OF_DAY] = 0
        date[Calendar.MINUTE] = 0
        date[Calendar.SECOND] = 0
        date[Calendar.MILLISECOND] = 0
        return date
    }

    /**
     * 通过毫秒数获取一个Calendar的实例
     */
    fun getCalendarByTimeMills(timeMills: Long) =
        Calendar.getInstance().also { it.timeInMillis = timeMills }

    /**
     * 获取某一天结束时间
     *
     * @return
     */
    fun getEndOfDayTime(calendar: Calendar): Calendar {
        val date = calendar.clone() as Calendar
        date[Calendar.HOUR_OF_DAY] = 23
        date[Calendar.MINUTE] = 59
        date[Calendar.SECOND] = 59
        date[Calendar.MILLISECOND] = 999
        return date
    }
}

/**
 * 将时间转换为日期字符串
 * yyyy-mm-dd
 * 如 2022-01-20
 */
fun Calendar.toDateString(): String {
    val sb = StringBuilder()
    sb.append(get(Calendar.YEAR)).append('-')
    (get(Calendar.MONTH) + 1).let {
        if (it < 10) {
            sb.append('0')
        }
        sb.append(it).append('-')
    }
    get(Calendar.DAY_OF_MONTH).let {
        if (it < 10) {
            sb.append('0')
        }
        sb.append(it)
    }
    return sb.toString()
}

/**
 * 获取相邻的日期
 * @param differenceDays 相差的天数
 */
fun Calendar.getNearDate(differenceDays: Int): Calendar {
    val c = this.clone() as Calendar
    c.add(Calendar.DAY_OF_YEAR, differenceDays)
    return c
}

/**
 * 将10以下的整数转化为两位数的String
 */
fun Int.toDoubleDigits() = if (this < 10) "0$this" else this.toString()

/**
 * 转换成如 14:24的格式
 */
fun Calendar.toHHMM(): String =
    "${get(Calendar.HOUR_OF_DAY).toDoubleDigits()}:${get(Calendar.MINUTE).toDoubleDigits()}"

/**
 * 转换成如 14:24:22的格式
 */
fun Calendar.toHHMMSS(): String =
    "${get(Calendar.HOUR_OF_DAY).toDoubleDigits()}:${get(Calendar.MINUTE).toDoubleDigits()}:${get(Calendar.SECOND).toDoubleDigits()}"


/**
 * 判断两个时间是不是同一天
 */
fun Calendar.isSameDay(anotherDay: Calendar): Boolean {
    return this[Calendar.YEAR] == anotherDay[Calendar.YEAR] && this[Calendar.DAY_OF_YEAR] == anotherDay[Calendar.DAY_OF_YEAR]
}

/**
 * 日历转短文字
 * 昨天   前天   11.22 周一   11.19 周五
 */
fun Calendar.toShortString(): String {
    val near = toNearDayString()
    return near
        ?: (this[Calendar.MONTH] + 1).toString() + "月" + this[Calendar.DAY_OF_MONTH] + "日"
}

/**
 * 当前时间：比如 14:24:47
 */
val currentTime: String
    get() {
        Calendar.getInstance().apply {
            return java.lang.StringBuilder()
                .append(get(Calendar.HOUR_OF_DAY).doubleDigit)
                .append(':')
                .append(get(Calendar.MINUTE).doubleDigit)
                .append(':')
                .append(get(Calendar.SECOND).doubleDigit)
                .toString()
        }
    }

/**
 * 当前日期：比如 2020年3月11日
 */
val currentDate: String
    get() {
        Calendar.getInstance().apply {
            return java.lang.StringBuilder()
                .append(get(Calendar.YEAR))
                .append("年")
                .append(get(Calendar.MONTH) + 1)
                .append("月")
                .append(get(Calendar.DAY_OF_MONTH))
                .toString()
        }
    }

/**
 * 当前是星期几
 */
val currentDayOfWeek: String
    get() = Calendar.getInstance().dayOfWeek

/**
 * 获取星期几
 */
val Calendar.dayOfWeek: String
    get() {
        var dayOfWeek = "星期"
        when (this[Calendar.DAY_OF_WEEK]) {
            1 -> dayOfWeek += "天"
            2 -> dayOfWeek += "一"
            3 -> dayOfWeek += "二"
            4 -> dayOfWeek += "三"
            5 -> dayOfWeek += "四"
            6 -> dayOfWeek += "五"
            7 -> dayOfWeek += "六"
        }
        return dayOfWeek
    }

val Int.doubleDigit: String
    get() = if (this < 10) "0$this" else this.toString()

/**
 * 将时间转换为中文日期字符串
 * 如 2022年1月20日
 */
val Calendar.cnDate: String
    get() {
        val sb = StringBuilder()
        sb.append(get(Calendar.YEAR)).append('年')
        sb.append(get(Calendar.MONTH) + 1).append('月')
        sb.append(get(Calendar.DAY_OF_MONTH)).append('日')
        return sb.toString()
    }

/**
 * 获取某一天的时间
 * 比如 13:25
 */
fun getTimeOfDay(timeMills: Long): String {
    val fen = timeMills / 1000 / 60
    val hour = (fen / 60) % 24
    return "${hour.toInt().doubleDigit}:${(fen % 60).toInt().doubleDigit}"
}


/**
 * 将[Date]类型转换成[Calendar]类型
 */
fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    return calendar
}

fun Calendar.toLocalDateTime() = LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())

fun LocalDateTime.toCalendar() = Calendar.getInstance().also {
    it.set(year, (monthValue - 1), dayOfMonth, minute, second)
}
