package com.dzf.mvvm.ui.main


import androidx.fragment.app.Fragment
import com.dzf.mvvm.App
import com.dzf.mvvm.databinding.ActivityTestEventBinding
import com.dzf.mvvm.event.EventCode
import com.dzf.mvvm.event.EventMessage
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.ui.main.adapter.FragmentPageAdapter
import java.util.*


class TestEventActivity :
    BaseActivity<BaseViewModel<ActivityTestEventBinding>, ActivityTestEventBinding>() {

    private val fragments: ArrayList<Fragment> = ArrayList()
    private val titles = arrayOf("最新", "热门", "我的")

    override fun initView() {

    }

    override fun initClick() {
        vb.btn.setOnClickListener {
            App.post(EventMessage(EventCode.REFRESH))
        }
    }

    override fun initData() {
        for (i in titles.indices) {
            fragments.add(MainFragment.getInstance(i))
            val tab = vb.tabLayout.newTab()
            tab.text = titles[i]
            vb.tabLayout.addTab(tab)
        }
        vb.tabLayout.setupWithViewPager(vb.viewPager, false)
        var pagerAdapter = FragmentPageAdapter(mContext, supportFragmentManager, fragments,titles)
        vb.viewPager.adapter = pagerAdapter

    }

}