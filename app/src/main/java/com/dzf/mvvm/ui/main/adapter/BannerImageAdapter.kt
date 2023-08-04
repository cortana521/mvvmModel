package com.dzf.mvvm.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dzf.mvvm.api.response.DataBeanResponse
import com.dzf.mvvm.utils.GlideUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2023/8/3 11:14
 * @Description : 文件描述
 */
class BannerImageAdapter(mData: List<DataBeanResponse>) :
    BannerAdapter<DataBeanResponse, BannerImageAdapter.BannerViewHolder>(mData) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = layoutParams
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: DataBeanResponse?,
        position: Int,
        size: Int
    ) {
        data?.imgUrl?.let {
            holder?.imageView?.let { it1 ->
                GlideUtils.loadHttpImg(
                    it1?.context, holder.imageView,
                    it
                )
            }
        }
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView as ImageView
    }
}