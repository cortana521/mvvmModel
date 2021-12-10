package com.dzf.mvvm.ui.login.model

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/15 16:01
 * @Description : 医生个人信息
 */
class DoctorInfRequest {
    var token: String? = null
    var headPath: String? = null
    var name: String? = null
    var sex: String? = null
    var areaNo: String? = null
    var born: Long = 0//出生日期
    var age: String? = null
    var address //地址，自己添加的，用于缓存到本地
            : String? = null
}