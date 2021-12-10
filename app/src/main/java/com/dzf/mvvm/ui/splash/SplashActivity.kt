package com.dzf.mvvm.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dzf.mvvm.databinding.ActivitySplashBinding
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.ui.login.LoginActivity
import com.dzf.mvvm.utils.SysUtils


class SplashActivity : BaseActivity<BaseViewModel<ActivitySplashBinding>, ActivitySplashBinding>() {

    override fun initView() {
        if (!this.isTaskRoot) {
            val mainIntent = intent
            val action = mainIntent.action
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermission()) {
//            requestPermissions(
//                arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ), 1001
//            )
//        } else {
        init()
//        }
    }

    private fun init() {
        SysUtils.initFiles()
        Handler().postDelayed({
//            getjumpActivity(ActivityARouter.LOGIN)
//            if (StringUtils.isEmpty(SPUtils.getInstance().getString(Config.TOKEN))) {
                startActivity(Intent(this, LoginActivity::class.java))
//            }else{
//                startActivity(Intent(this, MainActivity::class.java))
//            }
            finish()
        }, 2000)
    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init()
                } else {
                    Toast.makeText(mContext, "您拒绝了文件权限", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission_group.STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        else
            true
    }

}