package com.dzf.mvvm.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.dzf.mvvm.R
import com.dzf.mvvm.utils.DensityUtil


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/27 10:08
 * @Description : 文件描述
 */
class DrawDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    var mContext: Context = context
    var onClickEvent: OnClickEvent? = null
    var drawDot: TextView? = null
    var drawLine: TextView? = null
    var drawTemplate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.BottomSelectAnimation)
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.dialog_content_circle, null)
        initView(view)
        initLisenter()
        setContentView(view)
    }

    private fun initLisenter() {
        drawTemplate?.setOnClickListener {
            dismiss()
        }
        drawDot?.setOnClickListener {
            onClickEvent?.onTakingPicturesClick()
            dismiss()
        }

        drawLine?.setOnClickListener {
            onClickEvent?.onPhotoAlbumClick()
            dismiss()
        }
    }

    private fun initView(view: View) {
        drawDot = view.findViewById(R.id.text_draw_dot)
        drawLine = view.findViewById(R.id.text_draw_line)
        drawTemplate = view.findViewById(R.id.text_draw_arrow)
    }

    fun setTextContent(drawDot:String,drawLine:String){
        this.drawDot?.text = drawDot
        this.drawLine?.text = drawLine
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