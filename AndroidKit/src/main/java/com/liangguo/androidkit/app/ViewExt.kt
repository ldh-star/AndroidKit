package com.liangguo.androidkit.app

import android.content.res.Resources
import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.ViewPropertyAnimator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.viewpager2.widget.ViewPager2


/**
 * @author ldh
 * 时间: 2022/1/26 19:17
 * 邮箱: 2637614077@qq.com
 */

/**
 * 为View添加Y方向上的平移动画
 */
fun View.animTranslateY(
    targetTranslationY: Int,
    interpolator: Interpolator = DecelerateInterpolator(),
    duration: Long = 300L
): ViewPropertyAnimator = animate()
    .translationY(targetTranslationY.toFloat())
    .setInterpolator(interpolator)
    .setDuration(duration)


/**
 * dp转px
 */
inline val Float.dp2px: Float
    get() = this * (Resources.getSystem().displayMetrics.density) + 0.5f


/**
 * dp转px
 */
inline val Int.dp2px
    get() = toFloat().dp2px

/**
 * 切割View圆角
 *
 * @param pixel 输入进来的是像素点，调用时应使用[dp2px]。
 */
fun <V : View> V.clipRoundCorner(pixel: Float) = this.apply {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, pixel)
        }
    }
}


/**
 * 当ViewPager2的页面切换时会触发该操作，封装了[ViewPager2.registerOnPageChangeCallback]的接口。
 */
fun ViewPager2.doOnPageChange(action: (position: Int) -> Unit) =
    object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            action(position)
        }
    }.also {
        registerOnPageChangeCallback(it)
    }


/**
 * 设置View的宽和高都是match_parent
 * @return 那个View本身
 */
fun <V : View> V.matchParent() = this.modifySize(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT
)



/**
 * 设置View的宽和高
 * @return 那个View本身
 */
fun <V : View> V.modifySize(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
) = this.apply {
    layoutParams?.let {
        layoutParams.width = width
        layoutParams.height = height
        post(::invalidate)
    } ?: let {
        layoutParams = ViewGroup.LayoutParams(width, height)
    }
}