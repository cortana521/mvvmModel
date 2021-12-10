package com.byl.mvvm.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.byl.mvvm.R
import kotlinx.android.synthetic.main.layout_title_toolbar.view.*

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/14 14:38
 * @Description : 文件描述
 */
class DefaultTopTitleBarWhite : RelativeLayout {

    private lateinit var bgRll : RelativeLayout
    private lateinit var imgLeft : ImageView
    private lateinit var imgRight : ImageView
    private lateinit var tvLeft : TextView
    private lateinit var tvRight : TextView
    private lateinit var tvCenter : TextView

    private lateinit var titleLine : View

    constructor(context : Context?, attrs : AttributeSet?) : super(context, attrs){

        //init(context)要在retrieveAttributes(attrs)前调用
        //因为属性赋值，会直接赋值到控件上去。
        init(context)

        //retrieveAttributes(attrs: AttributeSet)方法只接受非空参数
        attrs?.let { retrieveAttributes(attrs) }
    }

    /**
     * 初始化view布局
     */
    fun init(context : Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.layout_title_toolbar,this,true)
        bgRll = view.findViewById(R.id.bg_rll)
        imgLeft = view.findViewById(R.id.img_left)
        imgRight = view.findViewById(R.id.iv_right)
        tvLeft = view.findViewById(R.id.tv_left)
        tvRight = view.findViewById(R.id.tv_right)
        tvCenter = view.findViewById(R.id.tv_title_center)
        titleLine = view.findViewById(R.id.title_line)
    }

    /**
     * 获取xml文件中的自定义属性并赋值
     */
    private fun retrieveAttributes(attrs: AttributeSet?){
        val typedArray : TypedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTopTitleBar)

        //设置背景色
        val titleBack = typedArray.getColor(R.styleable.CustomTopTitleBar_ctb_back, Color.parseColor("#FFFFFF"))
        bgRll.setBackgroundColor(titleBack)

        //设置底部分割线(默认显示)
        val isShowLine = typedArray.getBoolean(R.styleable.CustomTopTitleBar_ctb_show_line,true)
        titleLine.visibility = if (isShowLine) View.VISIBLE else View.GONE

        //设置标题内容
        val strTitle = typedArray.getString(R.styleable.CustomTopTitleBar_ctb_title)
        tvCenter.text = strTitle ?: resources.getString(R.string.app_name)

        //左侧
        //设置左边图片(默认显示)
        //隐藏-false 显示-true
        val isShowLeftImg = typedArray.getBoolean(R.styleable.CustomTopTitleBar_ctb_show_img_left,true)
        imgLeft.visibility = if(isShowLeftImg) View.VISIBLE else View.GONE

        //设置左侧文本(默认隐藏)
        //隐藏-false 显示-true
        val isShowLeftTv = typedArray.getBoolean(R.styleable.CustomTopTitleBar_ctb_show_text_left,false)
        tvLeft.visibility = if (isShowLeftTv) View.VISIBLE else View.GONE

        //设置左侧图片背景
        val drawableLeft = typedArray.getDrawable(R.styleable.CustomTopTitleBar_ctb_img_left)
        if(drawableLeft != null){
            imgLeft.setImageDrawable(drawableLeft)
        }

        //设置左侧文本内容
        val strLeft = typedArray.getString(R.styleable.CustomTopTitleBar_ctb_text_left)
        if(strLeft.isNullOrBlank()){
            tvLeft.text = resources.getString(R.string.def_back_left_con)
        }else{
            tvLeft.text = strLeft
        }


        //右侧
        //设置右侧图片(默认隐藏)
        //隐藏-false 显示-true
        val isShowRightImg = typedArray.getBoolean(R.styleable.CustomTopTitleBar_ctb_show_img_right,false)
        imgRight.visibility = if (isShowRightImg) View.VISIBLE else View.GONE

        //设置右侧文本(默认隐藏)
        //隐藏-false 显示-true
        val isShowRightTv = typedArray.getBoolean(R.styleable.CustomTopTitleBar_ctb_show_text_right,false)
        tvRight.visibility = if (isShowRightTv) View.VISIBLE else View.GONE

        //设置右侧图片背景
        val drawableRight = typedArray.getDrawable(R.styleable.CustomTopTitleBar_ctb_img_right)
        if(drawableRight != null){
            imgRight.setImageDrawable(drawableRight)
        }

        //设置右侧文本内容
        val strRight = typedArray.getString(R.styleable.CustomTopTitleBar_ctb_text_right)
        if(strRight.isNullOrBlank()){
            tvRight.text = resources.getString(R.string.def_back_right_con)
        }else{
            tvRight.text = strRight
        }

        //释放回收
        typedArray.recycle()

    }

    /**
     * 设置左侧文本、图片监听
     */
    fun setLeftClickListener(onClickListener: View.OnClickListener):DefaultTopTitleBarWhite{
        imgLeft.setOnClickListener(onClickListener)
        tvLeft.setOnClickListener(onClickListener)
        return this
    }

    /**
     * 设置左侧图片背景
     */
    fun setLeftImg(leftImg : Drawable):DefaultTopTitleBarWhite{
        imgLeft.visibility = View.VISIBLE
        imgLeft.setImageDrawable(leftImg)
        return this
    }

    /**
     * 设置左侧文本内容
     */
    fun setLeftText(leftTitle : String):DefaultTopTitleBarWhite{
        tvLeft.text = leftTitle
        return this
    }

    /**
     * 设置左侧文本颜色
     */
    fun setLeftTextColor(color: Int):DefaultTopTitleBarWhite{
        tvLeft.setTextColor(color)
        return this
    }

    /**
     * 设置左侧图片是否可见
     */
    fun setLeftImgVisible(visible : Boolean):DefaultTopTitleBarWhite{
        imgLeft.visibility = if(visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 设置左侧图片是否可见
     */
    fun setBottomLineVisible(visible : Boolean):DefaultTopTitleBarWhite{
        title_line.visibility = if(visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 设置左侧文本是否可见
     */
    fun setLeftTextVisible(visible: Boolean):DefaultTopTitleBarWhite{
        tvLeft.visibility = if(visible) View.VISIBLE else View.GONE
        return this
    }


    /**
     * 设置右侧文本、图片监听
     */
    fun setRightClickListener(onClickListener: View.OnClickListener):DefaultTopTitleBarWhite{
        imgRight.setOnClickListener(onClickListener)
        tvRight.setOnClickListener(onClickListener)
        return this
    }

    /**
     * 设置右侧图片背景
     */
    fun setRightImg(rightImg : Drawable):DefaultTopTitleBarWhite{
        imgRight.visibility = View.VISIBLE
        imgRight.setImageDrawable(rightImg)
        return this
    }

    /**
     * 设置右侧文本内容
     */
    fun setRightText(rightTitle : String):DefaultTopTitleBarWhite{
        tvRight.text = rightTitle
        return this
    }

    /**
     * 设置右侧文本颜色
     */
    fun setRightTextColor(color: Int):DefaultTopTitleBarWhite{
        tvRight.setTextColor(color)
        return this
    }

    /**
     * 设置右侧图片是否可见
     */
    fun setRightImgVisible(visible: Boolean):DefaultTopTitleBarWhite{
        imgRight.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 设置右侧文本是否可见
     */
    fun setRightTextVisible(visible: Boolean):DefaultTopTitleBarWhite{
        tvRight.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 设置中间标题文本内容
     */
    fun setCenterTitleText(title : String):DefaultTopTitleBarWhite{
        tvCenter.text = title
        return this
    }

    /**
     * 设置中间文本颜色
     */
    fun setCenterTitleTextColor(color: Int):DefaultTopTitleBarWhite{
        tvCenter.setTextColor(color)
        return this
    }

    /**
     * 获取标题文本内容
     */
    fun getCenterTitleText() : String {
        var title = ""
        if(tvCenter != null){
            title = tvCenter.text.toString().trim()
        }
        return title
    }

    /**
     * 设置标题栏背景色
     */
    fun setTitleBackColor(color: Int):DefaultTopTitleBarWhite{
        bgRll.setBackgroundColor(color)
        return this
    }
}