package com.dzf.mvvm.ui.login.model

import com.google.gson.annotations.SerializedName

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/12 15:18
 * @Description : 文件描述
 */
class RegisterResponse {
    var doctorNo: String? = null
    var areaNo: String? = null
    var phone: String? = null
    var name: String? = null
    var sex: String? = null
    var headPath: String? = null
    var token: String? = null
    @SerializedName("status")
    var statusX: String? = null
    var mobileNo: String? = null
    var role: String? = null
    var sig: String? = null

}