package com.dzf.mvvm.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2022/10/18 17:04
 * @Description : 文件描述
 */
object StringTextUtil {
    /**
     * 改变指定文字颜色
     */
    fun setTextContentColor(
        context: Context,
        text: String?,
        index: Int,
        end: Int,
        color: Int
    ): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(text)
        val colorSpan = ForegroundColorSpan(context.resources.getColor(color))
        spannableString.setSpan(colorSpan, index, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannableString
    }
}