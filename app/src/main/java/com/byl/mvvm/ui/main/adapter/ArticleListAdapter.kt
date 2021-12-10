package com.byl.mvvm.ui.main.adapter

import android.app.Activity
import com.blankj.utilcode.util.ImageUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.byl.mvvm.databinding.ItemArticleBinding
import com.byl.mvvm.base.BaseAdapter
import com.byl.mvvm.ui.main.model.ArticleBean
import com.byl.mvvm.utils.GlideUtils


class ArticleListAdapter(context: Activity, listDatas: ArrayList<ArticleBean>) :
    BaseAdapter<ItemArticleBinding, ArticleBean>(context, listDatas) {

    override fun convert(v: ItemArticleBinding, t: ArticleBean, position: Int) {
        t.envelopePic?.let { GlideUtils?.loadImage(mContext, it,v.ivCover) }
        v.tvTitle.text = t.title
        v.tvDes.text = t.desc
    }

}
