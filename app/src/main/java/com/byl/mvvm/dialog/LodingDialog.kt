package com.byl.mvvm.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.byl.mvvm.R


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/27 10:45
 * @Description : 文件描述
 */
class LodingDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    var mContext: Context = context
    private val mCancelable = false
    private var mRotateAnimation: RotateAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.CENTER)
        window?.setWindowAnimations(R.style.BottomSelectAnimation)

        var view: View = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null)
        initView(view)
        setContentView(view)
    }

    private fun initView(view: View) {
        setCancelable(mCancelable)
        val tv_loading = findViewById<TextView>(R.id.tv_loading)
//        val iv_loading = findViewById<ImageView>(R.id.iv_loading)
//        iv_loading.measure(0, 0)
//        mRotateAnimation = RotateAnimation(
//            0f, 360f,
//            (iv_loading.measuredWidth / 2).toFloat(), (iv_loading.measuredHeight / 2).toFloat()
//        )
//        mRotateAnimation!!.interpolator = LinearInterpolator()
//        mRotateAnimation!!.duration = 1000
//        mRotateAnimation!!.repeatCount = -1
//        iv_loading.startAnimation(mRotateAnimation)
    }

    override fun dismiss() {
//        mRotateAnimation!!.cancel();
        super.dismiss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
            // 屏蔽返回键
            return mCancelable;
        }
        return super.onKeyDown(keyCode, event)
    }
}