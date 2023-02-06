package com.dzf.mvvm.ui.login.loginfrag

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseFragment
import com.dzf.mvvm.databinding.FragmentLoginPasswordBinding
import com.dzf.mvvm.utils.CommonlyUsedInterface

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:51
 * @Description : 密码登录
 */
class PasswordLoginFragment : BaseFragment<PassWordViewModel, FragmentLoginPasswordBinding>(),
    View.OnClickListener {

    override fun initView() {
        vb.etLoginUsername.setText(SPUtils.getInstance()?.getString(Config.USER_PHONE))
    }

    override fun initClick() {
        CommonlyUsedInterface.setViewClick(this, vb.tvLoginForgetpw)
        CommonlyUsedInterface.setViewClick(this, vb.cbLoginPwd)
        CommonlyUsedInterface.setViewClick(this, vb.tvLogin)
        vb.etLoginUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 11) {
                    vb.etLoginPwd.requestFocus()
                    SPUtils.getInstance()?.put(Config.USER_PHONE, s.toString())
                    val imm =
                        mActivity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(vb.etLoginPwd, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        })

        vb.icLayout.ivImg.setOnClickListener {
            if (Config.AGREE_OR_NOT) {
                Config.AGREE_OR_NOT = false
                vb.icLayout.ivImg.setImageResource(R.drawable.null_round)
            } else {
                Config.AGREE_OR_NOT = true
                vb.icLayout.ivImg.setImageResource(R.drawable.yes_selector_sugar)
            }
        }
    }

    override fun initData() {
        if (StringUtils.isEmpty(SPUtils.getInstance()?.getString(Config.TOKEN))) {

        }
    }

    override fun lazyLoadData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cb_login_pwd -> // 是否查看密码
                ""
            R.id.tv_login -> //登录
                if (Config.AGREE_OR_NOT) {
                    vm.getLoginMessage(
                        vb.etLoginUsername.text.toString(),
                        vb.etLoginPwd.text.toString()
                    )
                } else {
                    ToastUtils.showShort("请阅读并同意服务协议和隐私政策")
                }

            R.id.tv_login_forgetpw -> //忘记密码
                ""
        }
    }

}