package com.dzf.mvvm.ui.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ColorUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.api.URLConstant
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.databinding.FragmentHomeBinding
import com.dzf.mvvm.ui.main.adapter.BannerImageAdapter
import com.dzf.mvvm.ui.main.model.ArticleListBean
import com.dzf.mvvm.ui.main.model.BannerInfoResponse
import com.dzf.mvvm.ui.main.model.DoctorInfRequest
import com.dzf.mvvm.ui.main.model.HomeFuncItemBean
import com.dzf.mvvm.utils.GlideUtils
import com.dzf.mvvm.utils.SysUtils
import com.dzf.mvvm.web.WebViewActivity
import com.youth.banner.indicator.CircleIndicator

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 16:03
 * @Description : 首页数据处理
 */
class HomeModel : BaseViewModel<FragmentHomeBinding>() {

    private var articlesData = MutableLiveData<ArticleListBean>()
    private var doctorMsg = MutableLiveData<DoctorInfRequest>()
    private var mBannerList = MutableLiveData<BannerInfoResponse>()
    private var mContext: Context? = null

    fun getArticleList(page: Int, isShowLoading: Boolean = false) {
        launch({ httpUtil.getArticleList(page) }, articlesData, isShowLoading)
    }

    fun getDoctorMsg(mContext: Context) {
        this.mContext = mContext
        launch({
            httpUtil.getDoctorInfo(
                "ANDROID",
                "android_Version--" + SysUtils.getVersionNum(mContext).toString() +
                        "_Manufactor--" + SysUtils.getDeviceBrand() + "_Model--" + SysUtils.getSystemModel() +
                        "_systemCode--" + SysUtils.getSystemVersion()
            )
        }, doctorMsg, isShowLoading = true)
    }

    private fun getBannerImgList() {
        launch({ httpUtil.getDoctorSlideBanner() }, mBannerList, isShowLoading = false)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun observe(fragment: Fragment, owner: LifecycleOwner) {
        articlesData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()
        })
        errorData.observe(owner, Observer {
            vb.refreshLayout.finishRefresh()
        })

        /** 医生个人资料 */
        doctorMsg.observe(owner, {
            it.let {
                getBannerImgList()
                vb.refreshLayout.finishRefresh()
                Config?.doubleIterator = it
                vb.tvHomeDoctorName.text = it?.name
                vb.ivHomeStatus.setImageResource(R.mipmap.certified_bac)
                vb.tvHomeStatus.setTextColor(ColorUtils.getColor(R.color.white))
                when (it?.status) {
                    "revise" ->
                        vb.tvHomeStatus?.text = "已认证"
                    "common" ->
                        vb.tvHomeStatus?.text = "已认证"
                    "init" ->
                        vb.tvHomeStatus?.text = "未认证"
                    "refuse" ->
                        vb.tvHomeStatus?.text = "被驳回"
                    "logout" ->
                        vb.tvHomeStatus?.text = "注销"
                    "seal", "hangup" ->
                        vb.tvHomeStatus?.text = "挂起"
                }
            }
        })

        /** 轮播图*/
        mBannerList.observe(owner, { it ->
            vb.banner.setAdapter(it?.slideList?.let { it1 -> BannerImageAdapter(it1) })
                .addBannerLifecycleObserver(fragment.activity)
                .setIndicator(CircleIndicator(fragment.activity))
                .setOnBannerListener { _, position ->
                    it.slideList[position]?.let {
                        openWeb(
                            it.title,
                            it.needParam,
                           it.link, ""
                        )
                    }
                }
        })
    }

    override fun getDataTokenExpiration(status: Int) {
        super.getDataTokenExpiration(status)
        vb.refreshLayout.finishRefresh()
        if (status == 2) {
            mContext?.let { goToLogin(it) }
        }
    }

    fun setHomeFuncData(): ArrayList<HomeFuncItemBean> {
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
        return mData!!
    }

    /**
     * 点击banner事件
     */
    private fun openWeb(title: String, needParam: String, link: String, content: String) {
        val intent = Intent(mContext,WebViewActivity::class.java)
        //不需要传递参数
        if (!needParam.isNullOrEmpty() && needParam == "y") intent?.putExtra("content", content)
        intent?.putExtra("title", title)
        mContext?.startActivity(intent)
    }
}