package com.byl.mvvm.ui.login

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils
import com.byl.mvvm.R
import com.byl.mvvm.arouter.ActivityARouter
import com.byl.mvvm.base.BaseActivity
import com.byl.mvvm.databinding.ActivityLoginBinding
import com.byl.mvvm.ui.login.loginfrag.CodeLoginFragment
import com.byl.mvvm.ui.login.loginfrag.PasswordLoginFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_login.*


/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:37
 * @Description : 登录界面
 */
@Route(path = ActivityARouter.LOGIN)
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    var tabs: Array<String> = arrayOf("验证码登录", "密码登录")
    val codelogin: CodeLoginFragment = CodeLoginFragment()
    val passwordlogin: PasswordLoginFragment = PasswordLoginFragment()

    override fun initView() {
        vb.layoutTitle
            .setRightTextVisible(true)
            .setRightText(StringUtils.getString(R.string.app_login_right_title))
            .setRightTextColor(ColorUtils.getColor(R.color.colorButton))
            .setCenterTitleText(StringUtils.getString(R.string.app_login_title))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
            .setLeftImgVisible(true)
            .setLeftImg(ResourceUtils.getDrawable(R.mipmap.icon_get_back))
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(false)
            .setRightImgVisible(false)
            .setLeftClickListener {
                finish()
            }
            .setRightClickListener {
                startActivity(Intent(mContext, RegisterActivity::class.java))
            }

        vb.vpMessageFg.adapter = object : FragmentStateAdapter(mContext) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> codelogin
                    else -> passwordlogin
                }
            }
        }

        TabLayoutMediator(tablayout, vb.vpMessageFg) { tab, position ->
            when (position) {
                0 -> tab.text = tabs[position]
                else -> tab.text = tabs[position]
            }
        }.attach()
    }

    override fun initClick() {

    }

    override fun initData() {
        getisFristLogin()
    }
}