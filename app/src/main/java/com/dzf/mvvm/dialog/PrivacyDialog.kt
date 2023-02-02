package com.dzf.mvvm.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.web.WebViewActivity
import kotlinx.android.synthetic.main.layout_privacy_policy_dialog.*
import kotlinx.android.synthetic.main.layout_privacy_policy_dialog.view.*

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2022/10/18 15:43
 * @Description : 文件描述
 */
class PrivacyDialog(context: Context?, themeResId: Int) : Dialog(context!!, themeResId) {

    private val mContext: Context = context!!
    private var onClickEvent: OnClickEvent? = null
    private var isSelcet: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(false)
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_privacy_policy_dialog, null)
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = 1000
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.setWindowAnimations(R.style.BottomSelectAnimation)
        window!!.attributes = layoutParams
        initData(view)
        initLisenter(view)
        setContentView(view)
    }

    private fun initData(view: View) {
        view.tv_content!!.text = Config.mPrivacyPolicy
        val span1 = SpannableStringBuilder(Config.AGREE_CONTENT)
        span1.setSpan(TextViewSpan1(), 8, 12, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        view.tv_protocol!!.text = span1
        span1.setSpan(
            TextViewSpan2(),
            15,
            Config.AGREE_CONTENT.length - 1,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        view.tv_protocol?.text = span1
        view.tv_protocol?.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initLisenter(view: View) {
        view.iv_img!!.setOnClickListener {
            if (!isSelcet!!) {
                isSelcet = true
                view.iv_img?.setImageResource(R.drawable.yes_selector_sugar)
            } else {
                isSelcet = false
                view.iv_img?.setImageResource(R.drawable.null_round)
            }
        }

        view.tv_btn?.setOnClickListener {
            if (isSelcet == false) {
                ToastUtils.showShort("请阅读并同意用户协议和隐私政策")
                return@setOnClickListener
            }
            dismiss()
            onClickEvent?.onAgreeturesClick()
        }

        view.tv_cancel?.setOnClickListener {
            dismiss()
            onClickEvent?.onNoAgreeAlbumClick()
        }

    }

    fun setOnClickEvent(listener: OnClickEvent) {
        this.onClickEvent = listener
    }


    interface OnClickEvent {
        fun onAgreeturesClick()

        fun onNoAgreeAlbumClick()
    }

    private class TextViewSpan1 : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = ColorUtils.getColor(R.color.text_color_1173CB)
            //设置是否有下划线
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            //点击事件
            var intent: Intent?
            intent = Intent()
            intent?.let {
                it.setClass(widget.context, WebViewActivity::class.java)
                it.putExtra("title", "用户协议")
                it.putExtra("url", Config.service_agreement + System.currentTimeMillis())
                ActivityUtils.startActivity(it)
            }
        }
    }

    private class TextViewSpan2 : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = ColorUtils.getColor(R.color.text_color_1173CB)
            //设置是否有下划线
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            //点击事件
            var intent: Intent?
            intent = Intent()
            intent?.let {
                it.setClass(widget.context, WebViewActivity::class.java)
                it.putExtra("title", "隐私政策")
                it.putExtra("url", Config.APP_PRIVACY + System.currentTimeMillis())
                ActivityUtils.startActivity(it)
            }
        }
    }
}