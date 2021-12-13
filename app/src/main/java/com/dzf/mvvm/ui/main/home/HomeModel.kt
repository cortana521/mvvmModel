package com.dzf.mvvm.ui.main.home

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.databinding.FragmentHomeBinding
import com.dzf.mvvm.ui.main.model.ArticleListBean
import com.dzf.mvvm.ui.main.model.HomeFuncItemBean

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:03
 * @Description : 文件描述
 */
class HomeModel : BaseViewModel<FragmentHomeBinding>() {

    var articlesData = MutableLiveData<ArticleListBean>()

    fun getArticleList(page: Int, isShowLoading: Boolean = false) {
        launch({ httpUtil.getArticleList(page) }, articlesData, isShowLoading)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observe(fragment: Fragment, owner: LifecycleOwner) {
        val mContext = fragment as HomeFragment
        articlesData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()

        })
        errorData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()
        })
    }

    fun setHomeFuncData(): ArrayList<HomeFuncItemBean>? {
        var mData: ArrayList<HomeFuncItemBean>? = ArrayList()
        //数据
        val homeFuncItem = HomeFuncItemBean(R.mipmap.not_appointment, "看诊列表", 0)
        val homeFuncItem1 = HomeFuncItemBean(R.mipmap.home_func_appointment, "我的预约", 0)
        val homeFuncItem2 = HomeFuncItemBean(R.mipmap.home_func_setting, "预约设置", 0)
        val homeFuncItem3 = HomeFuncItemBean(R.mipmap.appointment, "邀请用户", 0)
        val homeFuncItem4 = HomeFuncItemBean(R.mipmap.home_func_interrogation, "问诊单", 0)
        val homeFuncItem5 = HomeFuncItemBean(R.mipmap.home_func_invitedoc, "处方模板", 0)
        val homeFuncItem6 = HomeFuncItemBean(R.mipmap.home_func_consilia, "我的医案", 0)
        val homeFuncItem7 = HomeFuncItemBean(R.mipmap.home_func_inviteuser, "邀请医生", 0)
        val homeFuncItem10 = HomeFuncItemBean(R.mipmap.icon_academy, "金草学院", 0)
        val homeFuncItem8 = HomeFuncItemBean(R.mipmap.home_func_computer, "电脑传图", 0)
        mData?.add(homeFuncItem)
        mData?.add(homeFuncItem1)
        mData?.add(homeFuncItem2)
        mData?.add(homeFuncItem3)
        mData?.add(homeFuncItem4)
        mData?.add(homeFuncItem5)
        mData?.add(homeFuncItem6)
        mData?.add(homeFuncItem7)
        mData?.add(homeFuncItem10)
        mData?.add(homeFuncItem8)
        return mData
    }
}