package com.dzf.mvvm.api.response

open class BaseResult<T> (
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)