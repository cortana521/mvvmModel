package com.dzf.mvvm.ui.login.loginfrag

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.utils.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.databinding.FragmentLoginCodeBinding
import com.dzf.mvvm.ui.login.model.LoginResponse
import com.dzf.mvvm.ui.main.MainActivity
import com.dzf.mvvm.utils.TimerUnit

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 11:23
 * @Description : 文件描述
 */
class CodeViewLogin : BaseViewModel<FragmentLoginCodeBinding>() {

    var articlesData = MutableLiveData<String>()
    var loginData = MutableLiveData<LoginResponse>()
    var timer: TimerUnit? = null

    fun getPhoneCode(phone: String, isShowLoading: Boolean = true) {
        if (phone?.length < 11) {
            ToastUtils.showShort("请输入手机号码")
            return
        }
        launch({ httpUtil.getPhoneCode(phone) }, articlesData, isShowLoading)
    }

    fun getLoginMessage(
        phone: String,
        password: String,
        code: String,
        isShowLoading: Boolean = true
    ) {
        if (phone?.length < 11) {
            ToastUtils.showShort("请输入手机号码")
            return
        }
        if (TextUtils.isEmpty(code) && code.isNotEmpty() && (code?.length == 4 || code?.length == 6)) {
            ToastUtils.showShort("请输入验证码")
            return
        }
        launch({ httpUtil.getPasswordLogin(phone, password, code) }, loginData, isShowLoading)
    }

    override fun observe(fragment: Fragment, owner: LifecycleOwner) {
        super.observe(fragment, owner)
        articlesData.observe(owner, {
            if (timer == null) {
                timer = TimerUnit(vb.tvLoginGetCode)
            }
            timer?.startTime()
        })

        loginData.observe(owner, {
            it?.let { it1 ->
                SPUtils.getInstance()?.put(Config.TOKEN, it1?.token)
                SPUtils.getInstance()?.put(Config.UID, it1?.doctorNo)
                SPUtils.getInstance()?.put(Config.USER_AREANO, it1?.areaNo)
                SPUtils.getInstance()?.put(Config.USER_ROLE, it1?.role)
                SPUtils.getInstance()?.put(Config.USER_SIG, it1?.sig)
                fragment.startActivity(Intent(fragment.activity, MainActivity::class.java))
                fragment.activity?.finish()
            }
        })

        errorData?.observe(owner, {
            ToastUtils.showShort(it?.errMsg)
        })
    }


}