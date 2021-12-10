package com.dzf.mvvm.model

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/11/8 13:52
 * @Description :存放省以及所属市
 */
data class PCACodePO(
    val code: String,
    val name: String,
    val children: MutableList<CCodePO>
)