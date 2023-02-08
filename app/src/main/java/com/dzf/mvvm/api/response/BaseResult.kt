package com.dzf.mvvm.api.response

open class BaseResult<T> (
    val data: T,
    val status: Int,
    val msg: String
)