package com.dzf.mvvm

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/3 14:41
 * @Description : 文件描述
 */
class Config {

    companion object {
        val isDeBug = false
        val USER_PHONE = "phone"
        const val USER_SIG = "user_sig"

        //用户id
        const val UID = "uid"

        //用户角色
        const val USER_ROLE = "user_role"

        //用户地址码
        const val USER_AREANO = "user_areaNo"

        //token
        const val TOKEN = "token"

        //用户phone
        const val UPHONE = "phone"

        //医生验证信息
        const val QUALIFICATION_INF = "qualification_inf"

        /**
         * app的状态
         */
        const val STATUS_FORCE_KILLED = -1 //被后台强杀
        const val STATUS_TOKEN_INVALID = 1 //token失效
        const val SYSTEM_MAINTENANCE = 2 //系统维护
        const val STATUS_ONLINE = 3 //登录在线

        /* 密码正则匹配 */ //    public static final String PWD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,25}$";
        const val PWD_REG =
            "^(?![0-9]+$)(?![a-zA-Z]+$)(?![0-9A-Z]+$)(?![0-9a-z]+$)[a-zA-Z0-9]{8,18}"

        const val AGREE_CONTENT = "我已阅读并同意《用户协议》和《隐私政策》"
        const val mPrivacyPolicy =
            "    金草中医尊重并保护所有用户的个人隐私，为了能给您提供更具体的服务，金草中医会按照隐私政策的规定使用您披露的个人信息与剪切板。"
        const val APP_PRIVACY = "https://szwx.jctcm.com/html/app/app_privacy.html?v=" //网络图片url

        const val service_agreement = "https://szwx.jctcm.com/html/app/service_agreement.html?v=" //网络图片url

    }
}