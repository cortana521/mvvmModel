package com.dzf.mvvm.ui.login.loginfrag

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseFragment
import com.dzf.mvvm.databinding.FragmentLoginCodeBinding
import com.dzf.mvvm.utils.CommonlyUsedInterface
import com.dzf.mvvm.utils.KeyBoardUtil

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 11:23
 * @Description : 验证码登录
 */
class CodeLoginFragment() : BaseFragment<CodeViewLogin, FragmentLoginCodeBinding>(),
    View.OnClickListener {

    override fun initView() {
        vb.etLoginUsername.setText(SPUtils.getInstance()?.getString(Config.USER_PHONE))
    }

    override fun initClick() {
        CommonlyUsedInterface.setViewClick(this, vb.tvLogin)
        CommonlyUsedInterface.setViewClick(this, vb.tvLoginGetCode)
    }

    override fun initData() {
        vb.etLoginUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 11) {
                    KeyBoardUtil.hide(vb.etLoginUsername, mActivity)
                    SPUtils.getInstance()?.put(Config.USER_PHONE, s.toString())
                }
            }
        })
    }

    override fun lazyLoadData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login ->
                vm.getLoginMessage(vb.etLoginUsername.text.toString(),"",vb.etLoginCode.text.toString())

            R.id.tv_login_get_code ->
                vm.getPhoneCode(vb.etLoginUsername.text.toString())
        }
    }
}