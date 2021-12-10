package com.dzf.mvvm.api

import com.dzf.mvvm.api.response.BaseResult
import com.dzf.mvvm.ui.common.model.TestModel
import com.dzf.mvvm.ui.login.model.LoginResponse
import com.dzf.mvvm.ui.login.model.RegisterResponse
import com.dzf.mvvm.ui.main.model.ArticleListBean
import retrofit2.http.*


interface ApiService {

    @GET("test")
    suspend fun test(@QueryMap options: HashMap<String, String?>): BaseResult<TestModel>

    @Headers("BaseUrlName:article")//静态替换
    @GET("article/listproject/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResult<ArticleListBean>

    @POST("api/doctor/login")
    @FormUrlEncoded
    suspend fun getPasswordLogin(@Field("mobile") mobile:String,@Field("loginPwd") loginPwd:String,
    @Field ("code") code:String)
    : BaseResult<LoginResponse>

    @POST("api/msg/msgSend/docRetrievePwd")
    @FormUrlEncoded
    suspend fun getPhoneCode(@Field ("mobile") phone:String) : BaseResult<String>

    @POST("api/doctor/add")
    @FormUrlEncoded
    suspend fun getRegisterAccount(@FieldMap map:Map<String,String>) : BaseResult<RegisterResponse>
}