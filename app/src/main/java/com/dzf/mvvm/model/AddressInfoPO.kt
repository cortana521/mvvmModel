package com.dzf.mvvm.model

import com.dzf.mvvm.utils.IPickerViewData

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/8 13:53
 * @Description : 文件描述
 */
data class AddressInfoPO(
    //地区编码
    val code: String,
    //地区名称
    val name: String
) : IPickerViewData {
    override fun getPickerViewText(): String = name
}