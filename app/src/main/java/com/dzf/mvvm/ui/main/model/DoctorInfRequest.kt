package com.dzf.mvvm.ui.main.model

import com.dzf.mvvm.api.response.BaseResult

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/15 16:01
 * @Description : 医生个人信息
 */
data class DoctorInfRequest(
    val data: Data
)

data class Data(
    val areaNo: String,
    val city: String,
    val defaultGoodsPercent: Int,
    val defaultGranulesPercent: Int,
    val defaultPrescriptionPercent: Int,
    val departments: String,
    val doctorNo: String,
    val faceFlag: String,
    val flags: String,
    val goodsPercent: Int,
    val granulesPercent: Int,
    val headPath: String,
    val inviteCode: String,
    val isPublic: String,
    val medicineSecrecy: String,
    val mobileNo: String,
    val name: String,
    val no: String,
    val phone: String,
    val prescriptionPercent: Int,
    val reservationPrice: Int,
    val resource: String,
    val returnFlag: String,
    val role: String,
    val scheduleFlag: Int,
    val shareURL: String,
    val shareURLForMem: String,
    val sig: String,
    val signature: String,
    val status: String,
    val tempAreaNo: String,
    val token: String,
    val vCode: Int
)