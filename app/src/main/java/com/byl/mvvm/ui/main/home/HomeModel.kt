package com.byl.mvvm.ui.main.home

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.byl.mvvm.databinding.FragmentHomeBinding
import com.byl.mvvm.base.BaseViewModel
import com.byl.mvvm.ui.main.model.ArticleListBean

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
            vb.refreshLayout.finishLoadMore()

        })
        errorData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()
            vb.refreshLayout.finishLoadMore()
        })
    }
}