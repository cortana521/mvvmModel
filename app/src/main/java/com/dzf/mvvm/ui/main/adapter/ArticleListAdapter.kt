package com.dzf.mvvm.ui.main.adapter

import android.app.Activity
import com.dzf.mvvm.databinding.ItemArticleBinding
import com.dzf.mvvm.base.BaseAdapter
import com.dzf.mvvm.ui.main.model.ArticleBean
import com.dzf.mvvm.utils.GlideUtils


class ArticleListAdapter(context: Activity, listDatas: ArrayList<ArticleBean>) :
    BaseAdapter<ItemArticleBinding, ArticleBean>(context, listDatas) {

    override fun convert(v: ItemArticleBinding, t: ArticleBean, position: Int) {
        t.envelopePic?.let { GlideUtils?.loadImage(mContext, it,v.ivCover) }
        v.tvTitle.text = t.title
        v.tvDes.text = t.desc
    }

}
