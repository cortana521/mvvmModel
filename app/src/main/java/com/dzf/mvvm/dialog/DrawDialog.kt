package com.dzf.mvvm.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import com.dzf.mvvm.R
import com.dzf.mvvm.utils.DensityUtil
import kotlinx.android.synthetic.main.dialog_content_circle.*


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/27 10:08
 * @Description : 文件描述
 */
class DrawDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    private var mContext: Context = context
    private var onClickEvent: OnClickEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.BottomSelectAnimation)
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.dialog_content_circle, null)
        initLisenter()
        setContentView(view)
    }

    private fun initLisenter() {
        text_draw_arrow?.setOnClickListener {
            dismiss()
        }
        text_draw_dot?.setOnClickListener {
            onClickEvent?.onTakingPicturesClick()
            dismiss()
        }

        text_draw_line?.setOnClickListener {
            onClickEvent?.onPhotoAlbumClick()
            dismiss()
        }
    }

    fun setTextContent(drawDot:String,drawLine:String){
        this.text_draw_dot?.text = drawDot
        this.text_draw_line?.text = drawLine
    }

    override fun show() {
        super.show()
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.decorView.setPadding(
            DensityUtil.dp2px(mContext, 9.5f),
            0,
            DensityUtil.dp2px(mContext, 9.5f),
            DensityUtil.dp2px(mContext, 9.5f)
        )
        window!!.attributes = layoutParams
    }

    interface OnClickEvent {
        fun onTakingPicturesClick()

        fun onPhotoAlbumClick()
    }

    fun setCallBaceck(click: OnClickEvent) {
        this.onClickEvent = click
    }

}