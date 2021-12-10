package com.dzf.mvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.api.HttpUtil
import com.dzf.mvvm.api.error.ErrorResult
import com.dzf.mvvm.api.error.ErrorUtil
import com.dzf.mvvm.api.response.BaseResult
import com.dzf.mvvm.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.collections.set


open class BaseViewModel<VB : ViewBinding> : ViewModel() {

    private val AUTH_SECRET = "123456"//前后台协议密钥
    val httpUtil by lazy { HttpUtil.getInstance().getService() }
    var isShowLoading = MutableLiveData<Boolean>()//是否显示loading
    var errorData = MutableLiveData<ErrorResult>()//错误信息
    lateinit var vb: VB

    fun binding(vb: VB) {
        this.vb = vb
    }

    open fun observe(activity: Activity, owner: LifecycleOwner) {

    }

    open fun observe(fragment: Fragment, owner: LifecycleOwner) {

    }

    private fun showLoading() {
        isShowLoading.value = true
    }

    private fun dismissLoading() {
        isShowLoading.value = false
    }

    private fun showError(error: ErrorResult) {
        errorData.value = error
    }

    /**
     * 无参
     */
    open fun signNoParams(): LinkedHashMap<String, String?> {
        var params = LinkedHashMap<String, String?>()
        params["sign"] = getSign(params)
        return params
    }

    /**
     * 有参
     */
    open fun signParams(params: LinkedHashMap<String, String?>): LinkedHashMap<String, String?> {
        params["sign"] = getSign(params)
        return params
    }


    /**
     * 签名
     */
    private fun getSign(params: LinkedHashMap<String, String?>): String {
        val sb = StringBuilder()
        params.forEach {
            val key = it.key
            var value = ""
            if (!it.value.isNullOrEmpty()) {
                value = URLEncoder.encode(it.value as String?).replace("\\+", "%20")
            }
            sb.append("$key=$value&")
        }
        val s = sb.toString().substring(0, sb.toString().length - 1).toLowerCase() + AUTH_SECRET
        return encryption(s)
    }


    /**
     * MD5加密
     * @param plainText 明文
     * @return 32位密文
     */
    private fun encryption(plainText: String): String {
        var re_md5 = ""
        try {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            md.update(plainText.toByteArray())
            val b: ByteArray = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            re_md5 = buf.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return re_md5
    }

    /**
     * 请求接口，可定制是否显示loading和错误提示
     * block：闭包（功能代码块，定义了其，为返回值为BaseResult的协程），
     * 相当于 val block={ suspend { httpUtil.getArticleList(page) } }
     *       val result=block()
     */
    fun <T> launch(
        block: suspend CoroutineScope.() -> BaseResult<T>,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = true,
        isShowError: Boolean = true
    ) {
        if (isShowLoading) showLoading()
        viewModelScope.launch {
            try {
                val result = block()
                when (result.status) {
                    0,1 -> {//请求成功
                        liveData.value = result.data
                    }
                    2 -> {//token失效

                    }
                    else -> {
                        LogUtils.e("请求错误>>" + result.msg)
                        showError(ErrorResult(result.status, result.msg, isShowError))
                    }
                }
            } catch (e: Throwable) {//接口请求失败
                LogUtils.e("请求异常>>" + e.message)
                val errorResult = ErrorUtil.getError(e)
                errorResult.show = isShowError
                showError(errorResult)
            } finally {//请求结束
                dismissLoading()
            }
        }
    }

    /**
     * 跳转到登录页面
     */
    protected open fun goToLogin(mContext: Context) {
        if (!TextUtils.isEmpty(SPUtils.getInstance()?.getString(Config.TOKEN))) {
            ToastUtils.showShort("登录过期，请重新登录")
            SPUtils.getInstance()?.put(Config.TOKEN, "")
        }
        SPUtils.getInstance()?.put(Config.TOKEN, "")
        SPUtils.getInstance()?.put(Config.UID, "")
        //清除角色信息
        SPUtils.getInstance()?.put(Config.USER_ROLE, "")
        //重置药材库更新时间
//        SPUtils?.put(mContext, Config.MEDICINE_UPDATE_TIME, 0L)
//        SPUtils?.put(mContext, Config.MEDICINE, "")
        ActivityUtils.finishAllActivities()
        val intent = Intent(mContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(intent)
    }
}