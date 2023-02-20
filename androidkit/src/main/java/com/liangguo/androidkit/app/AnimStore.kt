package com.liangguo.androidkit.app

import android.view.animation.*


/**
 * @author ldh
 * 时间: 2022/1/26 19:19
 * 邮箱: 2637614077@qq.com
 */

/**
 * alpha动画的函数。
 * @param hide 显示还是隐藏的动画、
 * @return [AlphaAnimation]对象
 */
fun alphaAnim(
    hide: Boolean,
    duration: Int = 400,
    interceptor: Interpolator = LinearInterpolator()
): AlphaAnimation {
    val anim = if (hide) AlphaAnimation(1f, 0f) else AlphaAnimation(0f, 1f)
    anim.duration = duration.toLong()
    anim.interpolator = interceptor
    return anim
}

/**
 * 缩放动画的函数。
 * @return 一个缩放动画的对象
 */
fun scaleAnim(
    hide: Boolean,
    duration: Int = 400,
    fromScale: Float = 0.5f,
    toScale: Float = 1f,
    interpolator: Interpolator =
        if (hide)
            AccelerateInterpolator(2f)
        else
            DecelerateInterpolator(2f)
) = ScaleAnimation(
    if (!hide) fromScale else toScale,
    if (hide) fromScale else toScale,
    if (!hide) fromScale else toScale,
    if (hide) fromScale else toScale,
    Animation.RELATIVE_TO_SELF,
    0.5f,
    Animation.RELATIVE_TO_SELF,
    0.5f
).also {
    it.interpolator = interpolator
    it.duration = duration.toLong()
}
