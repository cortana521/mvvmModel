package com.byl.mvvm.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘工具类
 * Created by lyl on 2017/6/22.
 */
object KeyBoardUtil {
    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    fun show(mEditText: View?, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    fun hide(mEditText: View, mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
        }
    }

    /**
     * 强制显示软键盘
     * @param activity
     */
    fun showSoftKeyboard(activity: Activity) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(activity.window.decorView, InputMethodManager.SHOW_FORCED)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 强制隐藏软键盘
     *
     * @param activity
     */
    fun hideSoftKeyboard(activity: Activity) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0) //强制隐藏键盘
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 判断软键盘是否显示方法
     * false 显示  true 没有显示
     *
     * @param activity
     * @return
     */
    fun isSoftShowing(activity: Activity): Boolean {
        //获取当屏幕内容的高度
        val screenHeight = activity.window.decorView.height
        //获取View可见区域的bottom
        val rect = Rect()
        //DecorView即为activity的顶级view
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom + getSoftButtonsBarHeight(activity)
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getSoftButtonsBarHeight(activity: Activity): Int {
        val metrics = DisplayMetrics()
        //这个方法获取可能不是真实屏幕的高度
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        //获取当前屏幕的真实高度
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)
        val realHeight = metrics.heightPixels
        return if (realHeight > usableHeight) {
            realHeight - usableHeight
        } else {
            0
        }
    }

    fun getFocusChange(activity: Activity, editText: EditText) {
        try {
            editText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    editText.isFocusable = true
                    editText.isFocusableInTouchMode = true
                    editText.requestFocus()
                    // showListPopulWindow(etNum);
                    hide(editText, activity)
                    show(editText, activity)
                } else {
                    // 此处为失去焦点时的处理内容
                    editText.isFocusable = false
                    editText.isFocusableInTouchMode = false
                }
            }
            editText.setOnClickListener {
                try {
                    // 此处为得到焦点时的处理内容
                    editText.isFocusable = true
                    editText.isFocusableInTouchMode = true
                    editText.requestFocus()
                    // showListPopulWindow(etNum);
                    hide(editText, activity)
                    show(editText, activity)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}