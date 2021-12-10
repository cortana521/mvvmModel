package com.byl.mvvm.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText
import com.byl.mvvm.R

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/14 17:27
 * @Description : 自带清除的EditText
 */
@SuppressLint("AppCompatCustomView")
class ClearEditText : EditText {
    //    private Drawable imgInable;
    private var imgAble: Drawable? = null
    private var mContext: Context

    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        init()
    }

    private fun init() {
//        imgInable = mContext.getResources().getDrawable(R.drawable.delete_gray);
        imgAble = mContext.resources.getDrawable(R.mipmap.edit_clear)
        //事件监听
        addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                setDrawable()
            }
        })
        setDrawable()
    }

    //设置删除图片
    private fun setDrawable() {
        if (length() < 1) {
            //表示没有输入文本内容时显示的图片，个人觉得不需要设置默认图片
//            setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null)
        }
    }

    // 处理删除事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (imgAble != null && event.action == MotionEvent.ACTION_UP) {
            val eventX = event.rawX.toInt()
            val eventY = event.rawY.toInt()
            //            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            val rect = Rect()
            getGlobalVisibleRect(rect)
            rect.left = rect.right - 70
            if (rect.contains(eventX, eventY)) setText("")
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        startAnimation(shakeAnimation(5))
    }

    companion object {
        private const val TAG = "EditTextWithDel"

        /**
         * 晃动动画
         * @param counts 1秒钟晃动多少下
         * @return
         */
        fun shakeAnimation(counts: Int): Animation {
            val translateAnimation: Animation = TranslateAnimation(0f, 10f, 0f, 0f)
            translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
            translateAnimation.duration = 1000
            return translateAnimation
        }
    }
}
