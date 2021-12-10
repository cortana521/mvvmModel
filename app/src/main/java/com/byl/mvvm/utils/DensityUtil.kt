package com.byl.mvvm.utils

import android.content.Context
import android.util.TypedValue

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/27 10:06
 * @Description : 文件描述
 */
object DensityUtil {

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    fun dp2px(context: Context, dpFloat: Float): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpFloat,context.resources.displayMetrics).toInt()
    }
    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    fun sp2px(context: Context,spFloat: Float): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spFloat,context.resources.displayMetrics).toInt()
    }
    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2dp(context: Context,pxFloat: Float): Float{
        var scale : Float = context.resources.displayMetrics.density
        return (pxFloat/scale)
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxFloat: Float):Float {
        return (pxFloat / context.resources.displayMetrics.scaledDensity)
    }
}