package com.dzf.mvvm.ui.main.user

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ColorUtils
import com.dzf.mvvm.R
import com.dzf.mvvm.databinding.FragmentUserBinding
import com.dzf.mvvm.base.BaseFragment
import com.dzf.mvvm.ui.main.TestEventActivity
import com.dzf.mvvm.ui.main.adapter.ArticleListAdapter
import com.dzf.mvvm.ui.main.model.ArticleBean


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:01
 * @Description : 用户
 */
class UserFragment : BaseFragment<UserModel, FragmentUserBinding>() {

    var adapter: ArticleListAdapter? = null
    var list: ArrayList<ArticleBean>? = ArrayList()
    var page: Int = 0

    override fun initView() {
        vb.layoutTitle
            .setRightTextVisible(false)
            .setCenterTitleText(resources.getString(R.string.app_tab_home))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
            .setLeftImgVisible(false)
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(false)
            .setRightImgVisible(false)
            .setRightTextVisible(false)

    }

    override fun initClick() {

    }

    override fun initData() {
        adapter = ArticleListAdapter(mActivity, list!!)
        adapter!!.itemClick {
            startActivity(Intent(mActivity, TestEventActivity::class.java))
        }
        vb.mRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        vb.mRecyclerView.adapter = adapter

        vb.refreshLayout.setOnRefreshListener {//下拉刷新
            page = 0
            vm.getArticleList(page,true)
        }
        vb.refreshLayout.setOnLoadMoreListener {//上拉加载
            vm.getArticleList(++page,true)
        }
        vm.getArticleList(page, true)
    }

    override fun lazyLoadData() {

    }
}