package com.dzf.mvvm.ui.setting

import android.view.View
import com.blankj.utilcode.util.*
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.databinding.ActivitySettingBinding
import com.dzf.mvvm.model.SettingModel
import com.dzf.mvvm.utils.CommonlyUsedInterface

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/2/14 13:58
 * @Description : 设置页面
 */
class SettingActivity : BaseActivity<SettingModel, ActivitySettingBinding>(), View.OnClickListener {
    override fun initView() {
        vb.layoutTitle
            .setCenterTitleText(StringUtils.getString(R.string.app_system_setting))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
            .setLeftImgVisible(true)
            .setRightImgVisible(true)
            .setLeftImg(ResourceUtils.getDrawable(R.mipmap.arrow_left_back))
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(true)
            .setRightTextVisible(false)
            .setLeftClickListener {
                finish()
            }
    }

    override fun initClick() {
     CommonlyUsedInterface.setViewClick(this,vb.tvSettingLogout)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_setting_logout ->
                vm.getLoginOut()
        }
    }
}