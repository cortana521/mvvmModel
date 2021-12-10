package com.dzf.mvvm.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import com.dzf.mvvm.App
import com.dzf.mvvm.R
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Pattern


object SysUtils {

    fun dp2Px(context: Context, dp: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun px2Dp(context: Context, px: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }


    // 获取当前APP名称
    fun getAppName(context: Context): String? {
        val packageManager = context.packageManager
        val applicationInfo: ApplicationInfo
        applicationInfo = try {
            packageManager.getApplicationInfo(context.packageName, 0)
        } catch (e: java.lang.Exception) {
            return context.resources.getString(R.string.app_name)
        }
        return packageManager.getApplicationLabel(applicationInfo).toString()
    }

    fun getAppVersion(): String? {
        val context: Context = App.instance
        val manager: PackageManager = context.packageManager
        return try {
            val info: PackageInfo = manager.getPackageInfo(context.packageName, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "1.0.0"
        }
    }

    fun getAppVersionCode(): Int {
        val context: Context = App.instance
        val manager: PackageManager = context.packageManager
        return try {
            val info: PackageInfo = manager.getPackageInfo(context.packageName, 0)
            info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            1
        }
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    fun getSystemModel(): String? {
        return try {
            Build.MODEL
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    fun getDeviceBrand(): String? {
        return try {
            Build.BRAND
        } catch (e: Exception) {
            ""
        }
    }

    fun initFiles() {
        var file = File(Environment.getExternalStorageDirectory(), "MVVM/data")
        if (!file.exists()) file.mkdirs()
        file = File(Environment.getExternalStorageDirectory(), "MVVM/images")
        if (!file.exists()) file.mkdirs()
        file = File(Environment.getExternalStorageDirectory(), "MVVM/download")
        if (!file.exists()) file.mkdirs()
    }

    fun getScreenWidth(activity: Activity): Int {
        var width = 0
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        width = display.width
        return width
    }

    fun getScreenHeight(activity: Activity): Int {
        var height = 0
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        height = display.height
        return height
    }

    /**
     * 获取本地json文件
     * @return 本地json内容的字符串
     */
    fun getLocalJson(mContext: Context, file: String): String {
        val builder = StringBuilder()
        try {
            val inputStreamReader = InputStreamReader(mContext.assets.open(file), "UTF-8")
            val br = BufferedReader(inputStreamReader)
            var line: String
            //把文件内容读取进缓冲读取器（use方法会自动对BufferedReader进行关闭）
            br.use {
                while (true) {
                    line = it.readLine() ?: break //当有内容时读取一行数据，否则退出循环
                    println(line) //打印一行数据 } }
                    builder.append(line)
                }
            }
            br.close();
            inputStreamReader.close();
        } catch (e: IOException) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 输入框输入内容的约束
     * @param str  输入内容
     * @param type 输入内容的类型（密码、昵称等）
     * @return
     */
    fun regularInput(str: String?, type: String?): Boolean {
        if (TextUtils.isEmpty(str)) return false
        val pattern = Pattern.compile(type)
        val matcher = pattern.matcher(str)
        return matcher.matches()
    }
}