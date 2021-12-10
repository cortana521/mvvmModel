package com.byl.mvvm.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.byl.mvvm.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:07
 * @Description : 文件描述
 */
class GlideUtils {
    companion object {

        /**
         * 加载图片(String地址)
         */
        fun loadImage(context: Context, url: String, imageView: ImageView) {
            var strategy = DiskCacheStrategy.NONE
            if (url.startsWith("http://") || url.startsWith("https://")) {
                strategy = DiskCacheStrategy.RESOURCE
            }
            val options =
                RequestOptions().centerCrop().placeholder(R.color.white).error(R.color.white)
                    .dontAnimate().diskCacheStrategy(strategy)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载图片(int资源地址)
         */
        fun loadImage(context: Context, res: Int, imageView: ImageView) {
            var strategy = DiskCacheStrategy.NONE
            val options =
                RequestOptions().centerCrop().placeholder(R.color.white).error(R.color.white)
                    .dontAnimate().diskCacheStrategy(strategy)
            Glide.with(context).load(res).apply(options).into(imageView)
        }

        /**
         * 加载图片(uri)
         */
        fun loadImage(context: Context, uri: Uri, imageView: ImageView) {
            var strategy = DiskCacheStrategy.RESOURCE
            val options =
                RequestOptions().centerCrop().placeholder(R.color.white).error(R.color.white)
                    .dontAnimate().diskCacheStrategy(strategy)
            Glide.with(context).load(uri).apply(options).into(imageView)
        }

        /**
         * 加载图片(bitmap)
         */
        fun loadImage(context: Context, bitmap: Bitmap, imageView: ImageView) {
            var strategy = DiskCacheStrategy.RESOURCE
            val options =
                RequestOptions().centerCrop().placeholder(R.color.white).error(R.color.white)
                    .dontAnimate().diskCacheStrategy(strategy)
            Glide.with(context).load(bitmap).apply(options).into(imageView)
        }

        /**
         * 加载图片(String地址)---指定宽高
         */
        fun loadImage(
            context: Context,
            url: String,
            imageView: ImageView,
            width: Int,
            height: Int
        ) {
            var strategy = DiskCacheStrategy.NONE
            if (url.startsWith("http://") || url.startsWith("https://")) {
                strategy = DiskCacheStrategy.RESOURCE
            }
            val options =
                RequestOptions().centerCrop().placeholder(R.color.white).error(R.color.white)
                    .dontAnimate().override(width, height).diskCacheStrategy(strategy)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆形图片
         */
        fun loadCircleImage(context: Context, url: String, imageView: ImageView) {
            var strategy = DiskCacheStrategy.NONE
            if (url.startsWith("http://") || url.startsWith("https://")) {
                strategy = DiskCacheStrategy.RESOURCE
            }
            val options = RequestOptions().centerCrop().circleCrop().placeholder(R.color.white)
                .error(R.color.white).dontAnimate().diskCacheStrategy(strategy)
            Glide.with(context).load(url).apply(options).into(imageView)

        }

        /**
         * 加载圆形图片
         */
        fun loadRoundImage(context: Context, url: String, imageView: ImageView) {
            var strategy = DiskCacheStrategy.NONE
            if (url.startsWith("http://") || url.startsWith("https://")) {
                strategy = DiskCacheStrategy.RESOURCE
            }
            val options = RequestOptions().centerCrop().circleCrop().placeholder(R.color.white)
                .error(R.color.white).dontAnimate()
                .transform(
                    RoundedCornersTransformation(
                        15,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
                .diskCacheStrategy(strategy)

            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆形图片---指定圆角半径
         */
        fun loadRoundImage(context: Context, url: String, imageView: ImageView, radius: Int) {
            var strategy = DiskCacheStrategy.NONE
            if (url.startsWith("http://") || url.startsWith("https://")) {
                strategy = DiskCacheStrategy.RESOURCE
            }
            val options = RequestOptions().centerCrop().circleCrop().placeholder(R.color.white)
                .error(R.color.white).dontAnimate()
                .transform(
                    RoundedCornersTransformation(
                        radius,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
                .diskCacheStrategy(strategy)

            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）和半径
         */
        fun loadCustomRoundImage(
            context: Context,
            url: String,
            imageView: ImageView,
            type: RoundedCornersTransformation.CornerType,
            raduis: Int
        ) {
            var strategy = DiskCacheStrategy.NONE
            if (url.startsWith("http://") || url.startsWith("https://")) {
                strategy = DiskCacheStrategy.RESOURCE
            }
            val options = RequestOptions().centerCrop().circleCrop().placeholder(R.color.white)
                .error(R.color.white).dontAnimate()
                .transform(RoundedCornersTransformation(raduis, 0, type))
                .diskCacheStrategy(strategy)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

    }
}