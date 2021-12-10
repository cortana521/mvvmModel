package com.byl.mvvm.ui.login.model

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/4 17:06
 * @Description : 文件描述
 */
class LoginResponse {
    var doctorNo: String?=null
    var areaNo: String?=null
    var phone: String??=null
    var name: String?=null
    var sex: String?=null
    var headPath: String?=null
    var signature: String?=null
    var token: String?=null
    var statusX: String?=null
    var shareURL: String?=null
    var role: String?=null
    var activeTime: String?=null
    var mobileNo: String?=null
    var returnFlag: String?=null
    var sig: String?=null
    var scheduleFlag: Int = 0
    var prescriptionPercent: Int = 0
    var reservationPrice: Int = 0

    override fun toString(): String {
        return "DataBean(doctorNo=$doctorNo, areaNo=$areaNo, phone=$phone, name=$name, sex=$sex, headPath=$headPath, signature=$signature, token=$token, statusX=$statusX, shareURL=$shareURL, role=$role, activeTime=$activeTime, mobileNo=$mobileNo, returnFlag=$returnFlag, sig=$sig, scheduleFlag=$scheduleFlag, prescriptionPercent=$prescriptionPercent, reservationPrice=$reservationPrice)"
    }
}

