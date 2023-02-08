package com.dzf.mvvm.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.databinding.ActivitySplashBinding
import com.dzf.mvvm.base.BaseActivity
import com.dzf.mvvm.base.BaseViewModel
import com.dzf.mvvm.dialog.PrivacyDialog
import com.dzf.mvvm.ui.login.LoginActivity
import com.dzf.mvvm.ui.main.MainActivity
import com.dzf.mvvm.utils.SysUtils
import java.util.*


class SplashActivity : BaseActivity<BaseViewModel<ActivitySplashBinding>, ActivitySplashBinding>() {

    private var privacyDialog: PrivacyDialog? = null

    override fun initView() {
        if (!this.isTaskRoot) {
            val mainIntent = intent
            val action = mainIntent.action
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }
    }

    private fun init() {
        SysUtils.initFiles()
//            getjumpActivity(ActivityARouter.LOGIN)
        if (StringUtils.isEmpty(SPUtils.getInstance().getString(Config.TOKEN))) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Config.token = SPUtils.getInstance().getString(Config.TOKEN)!!
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }

    override fun initClick() {

    }

    override fun initData() {
        if (privacyDialog == null) {
            privacyDialog = PrivacyDialog(this, R.style.bottom_dialog)
        }
        if (SPUtils.getInstance().getString(Config.CONSENT_AGREEMENT).isNullOrEmpty()) {
            if (SPUtils.getInstance().getString(Config.TOKEN).isEmpty()) {
                privacyDialog!!.show()
                privacyDialog?.setOnClickEvent(object : PrivacyDialog.OnClickEvent {
                    override fun onAgreeturesClick() {
                        SPUtils.getInstance().put(Config.CONSENT_AGREEMENT, "同意")
                        init()
                    }

                    override fun onNoAgreeAlbumClick() {
                        finish()
                    }
                })
            } else {
                init()
            }
        } else {
            init()
        }
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