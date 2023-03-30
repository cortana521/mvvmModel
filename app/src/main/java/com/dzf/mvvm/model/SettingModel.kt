package com.dzf.mvvm.model

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.databinding.ActivitySettingBinding
import com.dzf.mvvm.ui.login.LoginActivity

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/2/14 17:09
 * @Description : 文件描述
 */
class SettingModel : BaseViewModel<ActivitySettingBinding>() {

    private var loginOut = MutableLiveData<String>()

    fun getLoginOut() {
        launch({ httpUtil.getLoginOut() }, loginOut)
    }

    override fun observe(activity: Activity, owner: LifecycleOwner) {
        super.observe(activity, owner)
        loginOut.observe(owner, {
            ActivityUtils.finishAllActivitiesExceptNewest()
            SPUtils.getInstance().put(Config.TOKEN, "")
            ActivityUtils.startActivity(Intent(activity, LoginActivity::class.java))
        })
    }

}