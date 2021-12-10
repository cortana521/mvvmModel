package com.byl.mvvm.ui.main

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.byl.mvvm.R
import com.byl.mvvm.arouter.ActivityARouter
import com.byl.mvvm.databinding.ActivityMainBinding
import com.byl.mvvm.base.BaseActivity
import com.byl.mvvm.ui.main.home.HomeFragment
import com.byl.mvvm.ui.main.adapter.FragmentPageAdapter
import com.byl.mvvm.ui.main.vm.MainActivityViewModel
import com.byl.mvvm.ui.main.message.MessageFragment
import com.byl.mvvm.ui.main.mine.MineFragment
import com.byl.mvvm.ui.main.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test_event.*

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:37
 * @Description : 主界面界面
 */
@Route(path = ActivityARouter.MAIN)
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    var homeFragment = HomeFragment()
    var userFragment = UserFragment()
    var messageFragment = MessageFragment()
    var mineFragment = MineFragment()
    var fragments: List<Fragment> = listOf(
        homeFragment,
        userFragment, messageFragment, mineFragment
    )

    override fun initView() {
        vb.nestedScrollview.adapter = FragmentPageAdapter(this, supportFragmentManager, fragments,null)
    }

    override fun initClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mBottomNavigationView)
    }

    val mBottomNavigationView = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        // 避免再次点击重复创建
        if (item.isChecked) {
            return@OnNavigationItemSelectedListener true
        }
        when (item.itemId) {
            R.id.tab_home -> {
                showFragment(0,fragments);
            }
            R.id.tab_user -> {
                showFragment(1,fragments);
            }
            R.id.tab_message -> {
                showFragment(2,fragments);
            }
            R.id.tab_mine -> {
                showFragment(3,fragments);
            }
        }
        true

    }

    override fun initData() {
        bottomNavigationView.selectedItemId = bottomNavigationView.getMenu().getItem(0).getItemId()
        showFragment(0,fragments)
    }

}