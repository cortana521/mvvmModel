package com.dzf.mvvm.ui.main.home

import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.R
import com.dzf.mvvm.databinding.FragmentHomeBinding
import com.dzf.mvvm.dialog.CommonDialog
import com.dzf.mvvm.event.EventCode
import com.dzf.mvvm.event.EventMessage
import com.dzf.mvvm.base.BaseFragment
import com.dzf.mvvm.ui.main.adapter.HomeFuncAdapter
import com.dzf.mvvm.ui.main.model.HomeFuncItemBean
import com.dzf.mvvm.utils.CommonlyUsedInterface
import com.dzf.mvvm.utils.StatusBarUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/9/1 15:57
 * @Description : 首页
 */
class HomeFragment : BaseFragment<HomeModel, FragmentHomeBinding>(), View.OnClickListener {
    var list: ArrayList<HomeFuncItemBean>? = ArrayList()
    var page: Int = 0

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(mActivity, ColorUtils.getColor(R.color.toolbar))
        vb.layoutTitle
            .setRightTextVisible(false)
            .setCenterTitleText(StringUtils.getString(R.string.app_home_title))
            .setCenterTitleTextColor(ColorUtils.getColor(R.color.shape_status_bg))
            .setRightImg(ResourceUtils.getDrawable(R.mipmap.saoyisao))
            .setLeftImgVisible(false)
            .setTitleBackColor(ColorUtils.getColor(R.color.white))
            .setBottomLineVisible(true)
            .setRightTextVisible(false)
            .setTitleBackColor(ColorUtils.getColor(R.color.toolbar))
            .setRightClickListener {
                CommonDialog.Builder(mActivity)
                    .setMessage("是否提交数据?")
                    .setNegativeButton(
                        StringUtils.getString(R.string.app_determine_btn),
                        DialogInterface.OnClickListener { p0, _ ->
                            p0.dismiss()
                        })
                    .setMessageColor(R.color.black)
                    .setPositiveButton(
                        StringUtils.getString(R.string.app_cancel_btn),
                        DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                        })
                    .setWith(0.8f)
                    .create()
                    .show()
            }

    }

    override fun initClick() {
        CommonlyUsedInterface.setViewClick(this, vb.tvHomeOnlineAppoint)
        CommonlyUsedInterface.setViewClick(this, vb.tvHomeParticles)
        CommonlyUsedInterface.setViewClick(this, vb.tvHomeCameraAppoint)
        CommonlyUsedInterface.setViewClick(this, vb.tvHomeLookAppoint)
        CommonlyUsedInterface.setViewClick(this, vb.rlAppoint)
        CommonlyUsedInterface.setViewClick(this, vb.rlPartyt)
        CommonlyUsedInterface.setViewClick(this, vb.rlGaoAuxiliary)
        CommonlyUsedInterface.setViewClick(this, vb.rlAcupuncture)
        vb.refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(re0freshLayout: RefreshLayout) {
                vm.getDoctorMsg(mActivity)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {

            }

        })
    }

    override fun initData() {
        vm.getDoctorMsg(mActivity)
        list?.addAll(vm.setHomeFuncData()!!)
        vb.rcyHome.layoutManager = GridLayoutManager(mActivity, 4)
        vb.rcyHome.adapter = list?.let { HomeFuncAdapter(mActivity, it) }
        vb.refreshLayout.setEnableLoadMore(false)
    }

    override fun lazyLoadData() {
        vm.getDoctorMsg(mActivity)
    }

    /**
     * 接收消息
     */
    override fun handleEvent(msg: EventMessage) {
        super.handleEvent(msg)
        if (msg.code == EventCode.REFRESH) {
            ToastUtils.showShort("主页：刷新")
            page = 0
            vm.getArticleList(page)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_home_openreceipt_appoint ->
                ToastUtils.showShort("点击了")
            R.id.tv_home_particles ->
                ""
            R.id.tv_home_camera_appoint ->
                ""
            R.id.tv_home_look_appoint ->
                ""
        }
    }
}