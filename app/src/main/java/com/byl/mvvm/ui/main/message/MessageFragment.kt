package com.byl.mvvm.ui.main.message

import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.StringUtils
import com.byl.mvvm.R
import com.byl.mvvm.databinding.FragmentMessageBinding
import com.byl.mvvm.base.BaseFragment


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:00
 * @Description : 文件描述
 */
class MessageFragment : BaseFragment<MessageModel, FragmentMessageBinding>(){
    override fun initView() {
        vb.layoutTitle
            .setRightTextVisible(false)
            .setCenterTitleText(StringUtils.getString(R.string.app_home_mssage))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
            .setLeftImgVisible(false)
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(true)
            .setRightImgVisible(false)
            .setRightTextVisible(false)
    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun lazyLoadData() {

    }
}