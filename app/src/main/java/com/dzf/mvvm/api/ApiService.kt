package com.dzf.mvvm.api

import com.dzf.mvvm.api.response.BaseResult
import com.dzf.mvvm.model.BaseStringResult
import com.dzf.mvvm.ui.common.model.TestModel
import com.dzf.mvvm.ui.main.model.DoctorInfRequest
import com.dzf.mvvm.ui.login.model.LoginResponse
import com.dzf.mvvm.ui.login.model.RegisterResponse
import com.dzf.mvvm.ui.main.model.ArticleListBean
import com.dzf.mvvm.ui.main.model.BannerInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap


interface ApiService {

    @GET("test")
    suspend fun test(@QueryMap options: HashMap<String, String?>): BaseResult<TestModel>

    @Headers("BaseUrlName:article")//静态替换
    @GET("article/listproject/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResult<ArticleListBean>

    /**
     * 登录
     */
    @POST("api/doctor/login")
    @FormUrlEncoded
    suspend fun getPasswordLogin(
        @Field("mobile") mobile: String, @Field("loginPwd") loginPwd: String,
        @Field("device") code: String
    )
            : BaseResult<LoginResponse>

    /**
     * 退出
     */
    @POST("api/doctor/logout")
    suspend fun getLoginOut(): BaseResult<String>

    /**
     * 获取医生信息
     */
    @POST("api/doctor/info")
    @FormUrlEncoded
    suspend fun getDoctorInfo(
        @Field("resource") type: String,
        @Field("appversion") version: String
    ): BaseResult<DoctorInfRequest>

    @POST("api/msg/msgSend/docRetrievePwd")
    @FormUrlEncoded
    suspend fun getPhoneCode(@Field("mobile") phone: String): BaseResult<String>

    @POST("api/doctor/add")
    @FormUrlEncoded
    suspend fun getRegisterAccount(@FieldMap map: Map<String, String>): BaseResult<RegisterResponse>

    @POST("api/doctor/slide/list")
    suspend fun getDoctorSlideBanner():BaseResult<BannerInfoResponse>

}