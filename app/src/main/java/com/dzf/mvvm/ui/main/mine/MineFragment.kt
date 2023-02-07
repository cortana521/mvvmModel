package com.dzf.mvvm.ui.main.mine

import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils
import com.dzf.mvvm.R
import com.dzf.mvvm.databinding.FragmentMineBinding
import com.dzf.mvvm.base.BaseFragment
import com.dzf.mvvm.utils.StatusBarUtil


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:01
 * @Description : 我的
 */
class MineFragment : BaseFragment<MineModel, FragmentMineBinding>() {

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(mActivity, ColorUtils.getColor(R.color.white))
        vb.layoutTitle
            .setRightTextVisible(false)
            .setCenterTitleText(StringUtils.getString(R.string.app_home_mine))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
            .setLeftImgVisible(false)
            .setRightImg(ResourceUtils.getDrawable(R.mipmap.setting))
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(true)
            .setRightTextVisible(false)
            .setRightClickListener {
                showDialog(StringUtils.getString(R.string.app_camera),StringUtils.getString(R.string.app_photo))
            }
    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun lazyLoadData() {

    }
}