package com.dzf.mvvm.ui.main

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.dzf.mvvm.R
import com.dzf.mvvm.arouter.ActivityARouter
import com.dzf.mvvm.databinding.ActivityMainBinding
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.ui.main.home.HomeFragment
import com.dzf.mvvm.ui.main.adapter.FragmentPageAdapter
import com.dzf.mvvm.ui.main.vm.MainActivityViewModel
import com.dzf.mvvm.ui.main.message.MessageFragment
import com.dzf.mvvm.ui.main.mine.MineFragment
import com.dzf.mvvm.ui.main.user.UserFragment
import com.dzf.mvvm.utils.StatusBarUtil
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
    private var homeFragment = HomeFragment()
    private var userFragment = UserFragment()
    private var messageFragment = MessageFragment()
    private var mineFragment = MineFragment()
    private var fragments: List<Fragment> = listOf(
        userFragment,  mineFragment
    )

    override fun initView() {
        vb.nestedScrollview.adapter = FragmentPageAdapter(this, supportFragmentManager, fragments,null)
    }

    override fun initClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mBottomNavigationView)
    }

    private val mBottomNavigationView = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        // 避免再次点击重复创建
        if (item.isChecked) {
            return@OnNavigationItemSelectedListener true
        }
        when (item.itemId) {
//            R.id.tab_home -> {
//                StatusBarUtil.setColorNoTranslucent(this, ColorUtils.getColor(R.color.toolbar))
//                showFragment(0,fragments);
//            }
            R.id.tab_user -> {
                StatusBarUtil.setColorNoTranslucent(this, ColorUtils.getColor(R.color.white))
                showFragment(0,fragments);
            }
//            R.id.tab_message -> {
//                StatusBarUtil.setColorNoTranslucent(this, ColorUtils.getColor(R.color.white))
//                showFragment(2,fragments);
//            }
            R.id.tab_mine -> {
                StatusBarUtil.setColorNoTranslucent(this, ColorUtils.getColor(R.color.white))
                showFragment(1,fragments);
            }
        }
        true

    }

    override fun initData() {
        bottomNavigationView.selectedItemId = bottomNavigationView.getMenu().getItem(0).itemId
        showFragment(0,fragments)
    }

}