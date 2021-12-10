package com.dzf.mvvm.ui.login

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.databinding.ActivityRegisterBinding
import com.dzf.mvvm.utils.CommonlyUsedInterface
import com.dzf.mvvm.utils.KeyBoardUtil

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/5 16:37
 * @Description : 注册页面
 */
class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(),
    View.OnClickListener {

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0x0001 ->
                    vm.initAddressPicker(this@RegisterActivity)
            }
        }
    }

    override fun initView() {
        mHandler.sendEmptyMessage(0x0001)
        vb.layoutTitle
            .setRightTextVisible(false)
            .setCenterTitleText(StringUtils.getString(R.string.app_register_title))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
            .setLeftImgVisible(true)
            .setLeftImg(ResourceUtils.getDrawable(R.mipmap.icon_get_back))
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(false)
            .setRightImgVisible(false)
            .setLeftClickListener {
                finish()
            }
    }

    override fun initClick() {
        CommonlyUsedInterface.setViewClick(this, vb.etSex)
        CommonlyUsedInterface.setViewClick(this, vb.tvLoginGetCode)
        CommonlyUsedInterface.setViewClick(this, vb.etRegisterCityrname)
        CommonlyUsedInterface.setViewClick(this, vb.tvRegister)
        CommonlyUsedInterface.setViewClick(this, vb.cbLoginPwd)
    }

    override fun initData() {
        vb.etRegisterUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 11) {
                    KeyBoardUtil.hide(vb.etRegisterUsername, mContext)
                    SPUtils.getInstance()?.put(Config.USER_PHONE, s.toString())
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login_get_code ->
                vm.getPhoneCode()
            R.id.et_sex ->
                showDialog(
                    StringUtils.getString(R.string.app_sex_man),
                    StringUtils.getString(R.string.app_sex_gril)
                )
            R.id.et_register_cityrname ->
                vm.showPickerView(this)
            R.id.tv_register ->
                vm.getRegisterAccount()
            R.id.cb_login_pwd ->
                ""
        }
    }

    override fun getTakingPicturesClick() {
        vb.etSex.setText(StringUtils.getString(R.string.app_sex_man))
    }

    override fun getPhotoAlbumClick() {
        vb.etSex.setText(StringUtils.getString(R.string.app_sex_gril))
    }
}