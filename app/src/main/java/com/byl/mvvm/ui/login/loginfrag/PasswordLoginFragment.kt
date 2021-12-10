package com.byl.mvvm.ui.login.loginfrag

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.byl.mvvm.Config
import com.byl.mvvm.R
import com.byl.mvvm.base.BaseFragment
import com.byl.mvvm.databinding.FragmentLoginPasswordBinding
import com.byl.mvvm.utils.CommonlyUsedInterface

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:51
 * @Description : 文件描述
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
                vm.getLoginMessage(
                    vb.etLoginUsername.text.toString(),
                    vb.etLoginPwd.text.toString()
                )
            R.id.tv_login_forgetpw -> //忘记密码
                ""
        }


    }

}