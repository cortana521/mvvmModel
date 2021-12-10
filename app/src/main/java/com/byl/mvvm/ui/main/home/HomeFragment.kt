package com.byl.mvvm.ui.main.home

import android.content.DialogInterface
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.byl.mvvm.R
import com.byl.mvvm.databinding.FragmentHomeBinding
import com.byl.mvvm.dialog.CommonDialog
import com.byl.mvvm.event.EventCode
import com.byl.mvvm.event.EventMessage
import com.byl.mvvm.base.BaseFragment
import com.byl.mvvm.ui.main.adapter.ArticleListAdapter
import com.byl.mvvm.ui.main.model.ArticleBean

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 15:57
 * @Description : 文件描述
 */
class HomeFragment : BaseFragment<HomeModel, FragmentHomeBinding>() {
    var adapter: ArticleListAdapter? = null
    var list: ArrayList<ArticleBean>? = ArrayList()
    var page: Int = 0

    override fun initView() {
        vb.layoutTitle
            .setRightTextVisible(false)
            .setCenterTitleText(StringUtils.getString(R.string.app_home_title))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.shape_status_bg))
            .setRightImg(ResourceUtils.getDrawable(R.mipmap.saoyisao))
            .setLeftImgVisible(false)
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(true)
            .setRightTextVisible(false)
            .setRightClickListener {
                CommonDialog.Builder(mActivity)
                    .setMessage("是否提交数据?")
                    .setNegativeButton(StringUtils.getString(R.string.app_determine_btn), DialogInterface.OnClickListener { p0, p1 ->
                        p0.dismiss()
                    })
                    .setMessageColor(R.color.black)
                    .setPositiveButton(StringUtils.getString(R.string.app_cancel_btn), DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    .setWith(0.8f)
                    .create()
                    .show()
            }

    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun lazyLoadData() {

    }

    /**
     * 接收消息
     */
    override fun handleEvent(msg: EventMessage) {
        super.handleEvent(msg)
        if (msg.code == EventCode.REFRESH) {
            ToastUtils.showShort( "主页：刷新")
            page = 0
            vm.getArticleList(page)
        }
    }
}