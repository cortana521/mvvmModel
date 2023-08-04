package com.dzf.mvvm.api.response

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/8/3 11:04
 * @Description : 文件描述
 */
data class MainAdResponse(
    /**
     * areaNo : 4403
     * content : 放假通知
     * createTime : 2019-07-22 14:55:55
     * expireTime : 2019-07-25 14:55:57
     * id : 1
     * operatorNo : AD20190619155730003
     * type : SYSTEM
     */
    val areaNo: String,
    val content: String,
    val id: Int,
    val createTime: String,
    val expireTime: String,
    val operatorNo: String,
    val type: String,
    val url: String

    )
