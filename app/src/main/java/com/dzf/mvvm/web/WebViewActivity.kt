package com.dzf.mvvm.web

import android.os.Build
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.dzf.mvvm.R
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.databinding.ActivityWebviewBinding
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * @ProjectName : mvvmModel
 * @Author : Dai Zhi Feng
 * @Time : 2022/10/19 9:24
 * @Description : 文件描述
 */
class WebViewActivity : BaseActivity<WebViewModel, ActivityWebviewBinding>() {

    override fun initView() {

    }

    override fun initClick() {

    }

    override fun initData() {
        intent.getStringExtra("title")?.let {
            vb?.layoutTitle.setRightTextVisible(true)
                .setCenterTitleText(it)
                .setCenterTitleTextColor(ColorUtils.getColor(R.color.black))
                .setLeftImgVisible(true)
                .setLeftImg(ResourceUtils.getDrawable(R.mipmap.icon_get_back))
                .setTitleBackColor(ColorUtils.getColor(R.color.white))
                .setBottomLineVisible(false)
                .setRightImgVisible(false)
                .setLeftClickListener {
                    finish()
                }
        }
        val webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        var webSettings = webView!!.settings

        webView?.webViewClient = webViewClient
        webSettings.javaScriptEnabled = true  // 开启 JavaScript 交互
        webSettings.setAppCacheEnabled(true) // 启用或禁用缓存
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT // 只要缓存可用就加载缓存, 哪怕已经过期失效 如果缓存不可用就从网络上加载数据
        webSettings.setAppCachePath(cacheDir.path) // 设置应用缓存路径

        // 缩放操作
        webSettings.setSupportZoom(false) // 支持缩放 默认为true 是下面那个的前提
        webSettings.builtInZoomControls = false // 设置内置的缩放控件 若为false 则该WebView不可缩放
        webSettings.displayZoomControls = false // 隐藏原生的缩放控件

        webSettings.blockNetworkImage = false // 禁止或允许WebView从网络上加载图片
        webSettings.loadsImagesAutomatically = true // 支持自动加载图片

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.safeBrowsingEnabled = true // 是否开启安全模式
        }

        webSettings.javaScriptCanOpenWindowsAutomatically = true // 支持通过JS打开新窗口
        webSettings.domStorageEnabled = true // 启用或禁用DOM缓存
        webSettings.setSupportMultipleWindows(true) // 设置WebView是否支持多窗口

        // 设置自适应屏幕, 两者合用
        webSettings.useWideViewPort = true  // 将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true  // 缩放至屏幕的大小
        webSettings.allowFileAccess = true // 设置可以访问文件

        webSettings.setGeolocationEnabled(true) // 是否使用地理位置

        intent.getStringExtra("url")?.let {
            webView?.loadUrl(it)
        }
    }

    //设置返回键的监听
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView!!.canGoBack()) {
                webView!!.goBack()  //返回上一个页面
                return true
            } else {
                finish()
                return true
            }
        }
        return false
    }

}