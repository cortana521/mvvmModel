package com.dzf.mvvm.api.retrofit

import com.dzf.mvvm.App
import com.dzf.mvvm.Config
import com.dzf.mvvm.api.ApiService
import com.dzf.mvvm.api.URLConstant
import com.dzf.mvvm.api.interceptor.LoggingInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
class RetrofitClient {

    companion object {
        fun getInstance() =
            SingletonHolder.INSTANCE

        private lateinit var retrofit: Retrofit
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }

    private var cookieJar: PersistentCookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(App.instance)
    )

    init {
        retrofit = Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLConstant.IP)
            .build()
    }


    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            //处理多BaseUrl,添加应用拦截器
            .addInterceptor(MoreBaseUrlInterceptor())
            .addInterceptor(LoggingInterceptor())
            .sslSocketFactory(SSLContextSecurity.createIgnoreVerifySSL("TLS"))
            .build()
    }

    class MoreBaseUrlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            //获取原始的originalRequest
            val originalRequest: Request = chain.request()
            //获取老的url
            val oldUrl = originalRequest.url()
            //获取originalRequest的创建者builder
            val builder = originalRequest.newBuilder()
            //获取头信息的集合如：manage,mdffx
            val headerValues: List<String> = originalRequest.headers("BaseUrlName")
            return if (headerValues.isNotEmpty()) {
                //删除原有配置中的值,就是namesAndValues集合里的值
                builder.removeHeader("article")
                //获取头信息中配置的value,如：manage或者mdffx
                val urlname = headerValues[0]
                var baseURL: HttpUrl? = null
                //根据头信息中配置的value,来匹配新的base_url地址
                if ("article" == urlname) {
                    baseURL = HttpUrl.parse(URLConstant.BASE_URL_RELEASE)
                } else if ("mdffx" == urlname) {
                    baseURL = HttpUrl.parse(URLConstant.IP)
                }
                //重建新的HttpUrl，需要重新设置的url部分
                val newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL!!.scheme()) //http协议如：http或者https
                    .host(baseURL!!.host()) //主机地址
                    .port(baseURL!!.port()) //端口
                    .build()

                //获取处理后的新newRequest
                val newRequest = builder.url(newHttpUrl).build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(originalRequest)
            }
        }

    }

    fun create(): ApiService = retrofit.create(ApiService::class.java)

}