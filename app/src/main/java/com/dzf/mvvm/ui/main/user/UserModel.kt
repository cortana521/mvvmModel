package com.dzf.mvvm.ui.main.user

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dzf.mvvm.databinding.FragmentUserBinding
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.ui.main.model.ArticleListBean


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:11
 * @Description : 用户数据
 */
class UserModel : BaseViewModel<FragmentUserBinding>(){

    var articlesData = MutableLiveData<ArticleListBean>()

    fun getArticleList(page: Int, isShowLoading: Boolean = false) {
        launch({ httpUtil.getArticleList(page) }, articlesData, isShowLoading)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observe(fragment: Fragment, owner: LifecycleOwner) {
        val mContext = fragment as UserFragment
        articlesData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()
            vb.refreshLayout.finishLoadMore()
            if (mContext.page == 0) mContext.list!!.clear()
            it.datas?.let { it1 -> mContext.list!!.addAll(it1) }
            mContext.adapter!!.notifyDataSetChanged()
        })
        errorData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()
            vb.refreshLayout.finishLoadMore()
        })
    }
}