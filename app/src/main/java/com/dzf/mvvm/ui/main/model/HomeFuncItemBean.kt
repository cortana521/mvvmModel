package com.dzf.mvvm.ui.main.model

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/12/13 15:26
 * @Description : 文件描述
 */
class HomeFuncItemBean(notAppointment: Int, s: String, i1: Int) {
    var imgRes = notAppointment  //图标
    var title: String? = s//名称
    var unReadNum = i1//未读信息数
}