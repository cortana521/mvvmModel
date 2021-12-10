package com.dzf.mvvm.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 15:45
 * @Description : 文件描述
 */
class NoScrollViewPage: ViewPager {

    private var isScroll: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setScroll(scroll: Boolean) {
        isScroll = scroll
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.isScroll && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.isScroll && super.onInterceptTouchEvent(event)

    }
}