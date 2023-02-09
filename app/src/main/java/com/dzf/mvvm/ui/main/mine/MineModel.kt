package com.dzf.mvvm.ui.main.mine

import android.content.Context
import com.dzf.mvvm.Config
import com.dzf.mvvm.api.URLConstant
import com.dzf.mvvm.databinding.FragmentMineBinding
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.utils.GlideUtils


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:10
 * @Description : 文件描述
 */
class MineModel : BaseViewModel<FragmentMineBinding>() {

    fun setdoctorName(name: String){
        vb.tvMineDoctorName.text = name
    }

    fun setDoctorHead(mContext: Context,headPath:String){
        GlideUtils.loadCircleImage(mContext, headPath,vb.civHeadPortrait)
    }
}