package com.byl.mvvm.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.blankj.utilcode.util.*
import com.byl.mvvm.Config
import com.byl.mvvm.R
import com.byl.mvvm.base.BaseViewModel
import com.byl.mvvm.databinding.ActivityRegisterBinding
import com.byl.mvvm.model.AddressInfoPO
import com.byl.mvvm.model.PCACodePO
import com.byl.mvvm.ui.login.model.DoctorInfRequest
import com.byl.mvvm.ui.login.model.RegisterResponse
import com.byl.mvvm.ui.main.MainActivity
import com.byl.mvvm.utils.SysUtils
import com.byl.mvvm.utils.TimerUnit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/5 16:37
 * @Description : 文件描述
 */
class RegisterViewModel : BaseViewModel<ActivityRegisterBinding>() {
    private val provinceItems = mutableListOf<String>()
    private val cityCode = mutableListOf<MutableList<AddressInfoPO>>()
    private val cityItems = mutableListOf<MutableList<String>>()
    private val areaItems = mutableListOf<MutableList<MutableList<String>>>()
    private var articlesData = MutableLiveData<String>()
    private var registerData = MutableLiveData<RegisterResponse>()
    private var areaCode: String? = null
    private var timer: TimerUnit? = null
    private var map: HashMap<String, String> = HashMap<String, String>()
    private val doctorInfRequest: DoctorInfRequest? = null

    fun getPhoneCode(isShowLoading: Boolean = true) {
        if (vb.etRegisterUsername.text.toString()?.length < 11) {
            ToastUtils.showShort("请输入手机号码")
            return
        }
        launch(
            { httpUtil.getPhoneCode(vb.etRegisterUsername.text.toString()) },
            articlesData,
            isShowLoading
        )
    }

    override fun observe(activity: Activity, owner: LifecycleOwner) {
        articlesData.observe(owner, {
            if (timer == null) {
                timer = TimerUnit(vb.tvLoginGetCode)
            }
            timer?.startTime()
        })

        registerData.observe(owner, {
            SPUtils.getInstance()?.put(Config.TOKEN, it.token)
            SPUtils.getInstance()?.put(Config.UID, it.doctorNo)
            SPUtils.getInstance()?.put(Config.UPHONE, it.phone)
            SPUtils.getInstance()?.put(Config.USER_ROLE, it.role)
            SPUtils.getInstance()?.put(Config.USER_SIG, it.sig)
            SPUtils.getInstance()?.put(Config.QUALIFICATION_INF, GsonUtils.toJson(doctorInfRequest))
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("action", Config.STATUS_ONLINE)
            activity.startActivity(intent)
            if (!TextUtils.isEmpty(it.doctorNo)) {
//                registerTIM(Constants.uid)
            }
            ToastUtils.showShort("注册成功")
            activity.finish()
        })

        errorData?.observe(owner, {
            ToastUtils.showShort(it?.errMsg)
        })
    }

    fun getRegisterAccount(isShowLoading: Boolean = true) {
        if (vb.etRegisterUsername.text.toString()?.length < 11) {
            ToastUtils.showShort("请输入手机号码")
            return
        }
        if (vb.etRegisterCode.text.toString()?.length <4) {
            ToastUtils.showShort("请输入验证码")
            return
        }
        map!!["phone"] = vb.etRegisterUsername.text.toString()
        map!!["pwd"] = vb.etRegisterPassword.text.toString()
        map!!["code"] = vb.etRegisterCode.text.toString()
        map!!["areaNo"] = areaCode!!
        map!!["inviteCode"] = vb.etRegisterInvitation.text.toString()
        map!!["age"] = vb.etAge.text.toString()
        map!!["sex"] = if (vb.etSex.text.toString() == "男") "MALE" else "FEMALE"
        map!!["name"] = vb.etRegisterDoctorname.text.toString()
//        doctorInfRequest!!.age = vb.etAge.text.toString()
//        doctorInfRequest!!.name = vb.etRegisterDoctorname.text.toString()
//        doctorInfRequest!!.address = vb.etRegisterCityrname.text.toString()
//        doctorInfRequest!!.areaNo = areaCode
//        doctorInfRequest!!.sex = if (vb.etSex.text.toString().equals("男")) "MALE" else "FEMALE"
            LogUtils.e(map)
        launch({ httpUtil.getRegisterAccount(map!!) }, registerData, isShowLoading)
    }

    /**
     * 初始化地址数据
     */
    fun initAddressPicker(mContext: Context) {
        //Json2Bean
        val pcaCodeList = Gson().fromJson<MutableList<PCACodePO>>(
            SysUtils.getLocalJson(mContext, "pcacode.json"),
            object : TypeToken<MutableList<PCACodePO>>() {}.type
        )
        //遍历省
        pcaCodeList.forEach { pcaCode ->
            //存放省内市区
            val cityList = mutableListOf<String>()
            val cityCode = mutableListOf<AddressInfoPO>()
            //存放省内所有辖区
            val areaList = mutableListOf<MutableList<String>>()
            //遍历省内市区
            pcaCode.children.forEach { cCode ->
                //添加省内市区
                cityList.add(cCode.name)
                cityCode.add(AddressInfoPO(cCode.code, cCode.name))
                //存放市内辖区
                val areas = mutableListOf<String>()
                //添加市内辖区
                cCode.children.forEach { addressInfo ->
                    areas.add(addressInfo.name)
                }
                areaList.add(areas)
            }
            //添加省份
            provinceItems.add(pcaCode.name)
            //添加市区
            cityItems.add(cityList)
            this.cityCode.add(cityCode)
            //添加辖区
            areaItems.add(areaList)
        }
    }

    /**
     * 展示选择器
     */
    fun showPickerView(mContext: Context) {// 弹出选择器
        KeyboardUtils.hideSoftInput(vb.etRegisterCityrname)
        val pvOptions = OptionsPickerBuilder(
            mContext,
            OnOptionsSelectListener { options1, options2, options3, v ->
                //省份
                provinceItems[options1]
                //城市
                cityItems[options1][options2]
                //辖区
                areaItems[options1][options2][options3]
                var tx: String = provinceItems[options1] +
                        cityItems[options1][options2]
//                +areaItems[options1][options2][options3]
                vb.etRegisterCityrname.setText(tx)
                areaCode = cityCode[options1][options2].code
            })
            .setTitleText(StringUtils.getString(R.string.app_city))
            .setDividerColor(ColorUtils.getColor(R.color.black))
            .setTextColorCenter(ColorUtils.getColor(R.color.black)) //设置选中项文字颜色
            .setSelectOptions(18, 2)
            .setContentTextSize(20)
            .build<String>()
        pvOptions.setPicker(provinceItems, cityItems, null)
        pvOptions.show()
    }
}