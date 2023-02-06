package com.dzf.mvvm.api.interceptor

import com.blankj.utilcode.util.SPUtils
import com.dzf.mvvm.Config
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/2/6 16:11
 * @Description : 头部token
 */
class TokenHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SPUtils.getInstance().getString(Config.TOKEN)
        val originalRequest = chain.request()
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        } else {
            val updateRequest =
                originalRequest.newBuilder().header("token", token).build()
            return chain.proceed(updateRequest)
        }
    }
}