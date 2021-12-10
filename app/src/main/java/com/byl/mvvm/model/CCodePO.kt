package com.byl.mvvm.model

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/8 13:53
 * @Description : 存放市以及所属辖区
 */
data class CCodePO(
    val code: String,
    val name: String,
    val children: MutableList<AddressInfoPO>
)