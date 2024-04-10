package com.dzf.mvvm.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.blankj.utilcode.util.*
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.databinding.ActivityRegisterBinding
import com.dzf.mvvm.model.AddressInfoPO
import com.dzf.mvvm.model.PCACodePO
import com.dzf.mvvm.ui.main.model.DoctorInfRequest
import com.dzf.mvvm.ui.login.model.RegisterResponse
import com.dzf.mvvm.ui.main.MainActivity
import com.dzf.mvvm.utils.SysUtils
import com.dzf.mvvm.utils.TimerUnit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/5 16:37
 * @Description : 注册数据
 */
class RegisterViewModel : BaseViewModel<ActivityRegisterBinding>() {
    private var registerData = MutableLiveData<RegisterResponse>()

    /**
     * 注册
     */
    fun getRegisterAccount(isShowLoading: Boolean = true) {
        if (vb.etRegisterUsername.text.toString()?.isNullOrEmpty()) {
            ToastUtils.showShort("请输入用户名")
            return
        }
        if (vb.etRegisterPassword.text.toString()?.isNullOrEmpty()) {
            ToastUtils.showShort("请输入密码")
            return
        }
        if (vb.etRepassword.text.toString()
                ?.isNullOrEmpty() && (vb.etRegisterPassword.text.toString() !=
                    vb.etRepassword.text.toString())
        ) {
            ToastUtils.showShort("2次密码输入不一致")
            return
        }
        launch(
            { httpUtil.getRegister(vb.etRegisterUsername.text.toString(),
                vb.etRegisterPassword.text.toString(), vb.etRepassword.text.toString()) },
            registerData,
            isShowLoading
        )
    }

    override fun observe(activity: Activity, owner: LifecycleOwner) {

        registerData.observe(owner, {
            SPUtils.getInstance()?.put(Config.NAME, it.nickname)
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("action", Config.STATUS_ONLINE)
            activity.startActivity(intent)
            ToastUtils.showShort("注册成功")
            activity.finish()
        })

        errorData?.observe(owner, {
            ToastUtils.showShort(it?.errMsg)
        })
    }




    /**
     * 初始化地址数据
     */
//    fun initAddressPicker(mContext: Context) {
//        //Json2Bean
//        val pcaCodeList = Gson().fromJson<MutableList<PCACodePO>>(
//            SysUtils.getLocalJson(mContext, "pcacode.json"),
//            object : TypeToken<MutableList<PCACodePO>>() {}.type
//        )
//        //遍历省
//        pcaCodeList.forEach { pcaCode ->
//            //存放省内市区
//            val cityList = mutableListOf<String>()
//            val cityCode = mutableListOf<AddressInfoPO>()
//            //存放省内所有辖区
//            val areaList = mutableListOf<MutableList<String>>()
//            //遍历省内市区
//            pcaCode.children.forEach { cCode ->
//                //添加省内市区
//                cityList.add(cCode.name)
//                cityCode.add(AddressInfoPO(cCode.code, cCode.name))
//                //存放市内辖区
//                val areas = mutableListOf<String>()
//                //添加市内辖区
//                cCode.children.forEach { addressInfo ->
//                    areas.add(addressInfo.name)
//                }
//                areaList.add(areas)
//            }
//            //添加省份
//            provinceItems.add(pcaCode.name)
//            //添加市区
//            cityItems.add(cityList)
//            this.cityCode.add(cityCode)
//            //添加辖区
//            areaItems.add(areaList)
//        }
//    }

    /**
     * 展示选择器
     */
//    fun showPickerView(mContext: Context) {// 弹出选择器
//        val pvOptions = OptionsPickerBuilder(
//            mContext,
//            OnOptionsSelectListener { options1, options2, options3, v ->
//                //省份
//                provinceItems[options1]
//                //城市
//                cityItems[options1][options2]
//                //辖区
//                areaItems[options1][options2][options3]
//                var tx: String = provinceItems[options1] +
//                        cityItems[options1][options2]
////                +areaItems[options1][options2][options3]
//                areaCode = cityCode[options1][options2].code
//            })
//            .setTitleText(StringUtils.getString(R.string.app_city))
//            .setDividerColor(ColorUtils.getColor(R.color.black))
//            .setTextColorCenter(ColorUtils.getColor(R.color.black)) //设置选中项文字颜色
//            .setSelectOptions(18, 2)
//            .setContentTextSize(20)
//            .build<String>()
//        pvOptions.setPicker(provinceItems, cityItems, null)
//        pvOptions.show()
//    }
}