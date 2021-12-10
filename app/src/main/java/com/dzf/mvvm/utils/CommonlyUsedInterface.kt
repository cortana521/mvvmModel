package com.dzf.mvvm.utils

import android.view.View

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/3 14:26
 * @Description : 常用类
 */
object CommonlyUsedInterface {

    fun setViewClick(listener: View.OnClickListener, vararg views: View) {
        for (it in views) {
            it.setOnClickListener(listener)
        }
    }
}