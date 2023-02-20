package com.liangguo.myutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liangguo.androidkit.app.ToastUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ToastUtil.success("哈哈哈")
    }
}