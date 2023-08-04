package com.dzf.mvvm.ui.main.model

import com.dzf.mvvm.api.response.DataBeanResponse
import com.dzf.mvvm.api.response.MainAdResponse

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/8/3 11:00
 * @Description : 文件描述
 */
data class BannerInfoResponse(
    /**
     * 问诊单未读数量
     */
    val inquiryCount: Int,
    /**
     * 预约待看诊数量
     */
    val reservationCount: Int,
    /**
     * 公告
     */
    val announce: MainAdResponse,
    /**
     * 待确认看诊数量
     */
    val confirmCount: String,
    /**
     * 图片前缀
     */
    val imgDomain: String,

    val slideList: List<DataBeanResponse>
)