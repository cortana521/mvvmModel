package com.dzf.mvvm.utils

import android.os.Handler
import android.widget.TextView

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/5 14:50
 * @Description : 文件描述
 */
class TimerUnit (private val textView: TextView) : Handler() {
    private var defaultTime = 60
    private var time = defaultTime
    private var isShowEndText = true

    private var timeEndListener: OnTimeEndListener? = null

    private var runnable: Runnable = object : Runnable {
        override fun run() {
            time--
            if (time == 0) {
                endtTime()
                return
            }
            textView.text = String.format("%ds", time)
            postDelayed(this, 1000)
        }
    }

    fun setTimeEndListener(timeEndListener: OnTimeEndListener) {
        this.timeEndListener = timeEndListener
    }

    fun setShowEndText(showEndText: Boolean) {
        isShowEndText = showEndText
    }


    fun setTime(time: Int) {
        this.defaultTime = time
        this.time = defaultTime
    }

    fun startTime() {
        post(runnable)
        textView.isEnabled = false
    }


    fun pauseTime() {
        removeCallbacks(runnable)
        time = defaultTime
    }

    fun endtTime() {
        if (isShowEndText) {
            textView.text = "重新获取"
        }
        textView.isEnabled = true
        removeCallbacks(runnable)
        time = defaultTime
        if (timeEndListener != null) {
            timeEndListener!!.timeEnd()
        }
    }

    interface OnTimeEndListener {
        fun timeEnd()
    }
}