package com.dzf.mvvm.api.response

open class BaseResult<T> {
    val data: T? = null
    val status: Int = 0
    val msg: String? = null
    override fun toString(): String {
        return "BaseResult(data=$data, status=$status, msg=$msg)"
    }
}