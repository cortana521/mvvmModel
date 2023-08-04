package com.dzf.mvvm.api.response

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/8/3 11:07
 * @Description : 文件描述
 */
data class DataBeanResponse(
    /**
     * id
     */
    private var id: Int,
    /**
     * 图片url
     */
    val imgUrl: String,
    /**
     * 跳转链接
     */
    val link: String,
    /**
     * 活动标题
     */
    val title: String,

    /**
     * 是否需要传值
     * @return
     */
    val needParam: String
)
