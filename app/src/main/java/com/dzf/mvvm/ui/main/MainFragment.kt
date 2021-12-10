package com.dzf.mvvm.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzf.mvvm.databinding.FragmentMainBinding
import com.dzf.mvvm.base.BaseFragment
import com.dzf.mvvm.ui.main.adapter.ArticleListAdapter
import com.dzf.mvvm.ui.main.model.ArticleBean
import com.dzf.mvvm.ui.main.vm.MainFragmentViewModel

class MainFragment : BaseFragment<MainFragmentViewModel, FragmentMainBinding>() {

    var id: Int? = 0
    var adapter: ArticleListAdapter? = null
    var list: ArrayList<ArticleBean>? = null
    var page: Int = 0

    companion object {
        fun getInstance(id: Int): MainFragment {
            val fragment = MainFragment()
            val b = Bundle()
            b.putInt("id", id)
            fragment.arguments = b
            return fragment
        }
    }

    override fun initView() {
        id = arguments?.get("id") as Int?
        list = ArrayList()
        adapter = ArticleListAdapter(mActivity, list!!)
        adapter!!.itemClick {
            startActivity(Intent(mActivity, TestEventActivity::class.java))
        }
        vb.mRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        vb.mRecyclerView.adapter = adapter

        vb.refreshLayout.setOnRefreshListener {//下拉刷新
            page = 0
            vm.getArticleList(page)
        }
        vb.refreshLayout.setOnLoadMoreListener {//上拉加载
            vm.getArticleList(++page)
        }
    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun lazyLoadData() {
        vm.getArticleList(page)
    }
}