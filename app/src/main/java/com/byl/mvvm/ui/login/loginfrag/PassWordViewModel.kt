package com.byl.mvvm.ui.login.loginfrag

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.byl.mvvm.Config
import com.byl.mvvm.base.BaseViewModel
import com.byl.mvvm.databinding.FragmentLoginPasswordBinding
import com.byl.mvvm.ui.login.model.LoginResponse
import com.byl.mvvm.ui.main.MainActivity

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:52
 * @Description : 文件描述
 */
class PassWordViewModel : BaseViewModel<FragmentLoginPasswordBinding>() {

    var articlesData = MutableLiveData<LoginResponse>()

    fun getLoginMessage(phone: String, password: String, isShowLoading: Boolean = true) {
        launch({ httpUtil.getPasswordLogin(phone, password,"") }, articlesData, isShowLoading)
    }

    override fun observe(fragment: Fragment, owner: LifecycleOwner) {
        articlesData?.observe(owner, {
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