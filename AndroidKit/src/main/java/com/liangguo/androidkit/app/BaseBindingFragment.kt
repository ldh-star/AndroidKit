package com.liangguo.androidkit.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * @author ldh
 * 时间: 2022/3/27 14:23
 * 邮箱: 2637614077@qq.com
 */
open class BaseBindingFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null

    @IdRes
    protected open val layoutResId = -1

    val binding: T
        get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (layoutResId == -1) throw IllegalArgumentException("继承BaseBindingFragment必须重写layoutResId或onCreateView")
        _binding = DataBindingUtil.inflate(
            inflater,
            layoutResId,
            container,
            false
        )
        initViews()
        initViewListeners()
        initDataListener()
        return binding.root
    }

    protected open fun initViews() {}

    protected open fun initViewListeners() {}

    protected open fun initDataListener() {}

}