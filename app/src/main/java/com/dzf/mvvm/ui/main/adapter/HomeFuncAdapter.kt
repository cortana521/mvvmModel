package com.dzf.mvvm.ui.main.adapter

import android.app.Activity
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.base.BaseAdapter
import com.dzf.mvvm.databinding.ItemHomeFuncBinding
import com.dzf.mvvm.ui.main.model.HomeFuncItemBean
import com.dzf.mvvm.widget.clicks

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/12/13 15:24
 * @Description : 文件描述
 */
class HomeFuncAdapter(mContext: Activity, list: ArrayList<HomeFuncItemBean>) :
    BaseAdapter<ItemHomeFuncBinding, HomeFuncItemBean>(
        mContext, list
    ) {
    override fun convert(v: ItemHomeFuncBinding, t: HomeFuncItemBean, position: Int) {
        v.ivHomeFuncImg.setImageResource(t.imgRes)
        v.tvHomeFuncTitle.text = t.title
        v.rlItem.clicks {
            ToastUtils.showLong(t.title)
        }
    }
}