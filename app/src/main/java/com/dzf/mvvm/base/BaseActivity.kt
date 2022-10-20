package com.dzf.mvvm.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.Config
import com.dzf.mvvm.R
import com.dzf.mvvm.api.error.ErrorResult
import com.dzf.mvvm.dialog.DrawDialog
import com.dzf.mvvm.dialog.LodingDialog
import com.dzf.mvvm.event.EventCode
import com.dzf.mvvm.event.EventMessage
import com.dzf.mvvm.ui.main.MainActivity
import com.dzf.mvvm.utils.GenericParadigmUtil
import com.dzf.mvvm.utils.StatusBarUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


abstract class BaseActivity<VM : BaseViewModel<VB>, VB : ViewBinding> : AppCompatActivity() {
    lateinit var mContext: FragmentActivity
    lateinit var vm: VM
    lateinit var vb: VB
    private var drawDialog: DrawDialog? = null
    private var loadingDialog: LodingDialog? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        super.onCreate(savedInstanceState)
        initResources()
        var pathfinders = ArrayList<GenericParadigmUtil.Pathfinder>()
        pathfinders.add(GenericParadigmUtil.Pathfinder(0, 0))
        val clazzVM = GenericParadigmUtil.parseGenericParadigm(javaClass, pathfinders) as Class<VM>
        vm = ViewModelProvider(this).get(clazzVM)

        pathfinders = ArrayList()
        pathfinders.add(GenericParadigmUtil.Pathfinder(0, 1))
        val clazzVB = GenericParadigmUtil.parseGenericParadigm(javaClass, pathfinders)
        val method = clazzVB!!.getMethod("inflate", LayoutInflater::class.java)
        vb = method.invoke(null, layoutInflater) as VB

        vm.binding(vb)
        vm.observe(this, this)

        setContentView(vb.root)

        mContext = this
        init()
        initView()
        initClick()
        initData()
        LogUtils.e(getClassName())
    }

    /**
     * 防止系统字体影响到app的字体
     *
     * @return
     */
    open fun initResources(): Resources? {
        val res: Resources = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    private var mCurrentFragment: Fragment? = null

    /**
     * 使用show、hide来管理fragment
     */
    open fun showFragment(position: Int, fragments: List<Fragment>) {
        val fragment: Fragment = fragments.get(position)
        if (null != fragment && mCurrentFragment !== fragment) {
            val transaction: FragmentTransaction =
                getSupportFragmentManager().beginTransaction()
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment!!)
            }
            mCurrentFragment = fragment
            if (!fragment.isAdded) {
                transaction.add(R.id.frame_content, fragment)
            } else {
                transaction.show(fragment)
            }
            transaction.commit()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    //事件传递
    @Subscribe
    fun onEventMainThread(msg: EventMessage) {
        handleEvent(msg)
    }

    open fun getClassName(): String? {
        val className = "BaseActivity"
        try {
            return javaClass.name
        } catch (e: Exception) {
        }
        return className
    }

    abstract fun initView()

    abstract fun initClick()

    abstract fun initData()

    private fun init() {
        StatusBarUtil.setColorForSwipeBack(this, ColorUtils.getColor(R.color.white))
        StatusBarUtil.darkMode(this, true)
        EventBus.getDefault().register(this)
        //loading
        (vm as BaseViewModel<*>).isShowLoading.observe(this, Observer {
            if (it) showLoading() else dismissLoding()
        })
        //错误信息
        (vm as BaseViewModel<*>).errorData.observe(this, Observer {
            if (it.show) ToastUtils.showShort(it.errMsg)
            errorResult(it)
        })
    }


    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LodingDialog(mContext, R.style.LoadingDialog)
        }
        loadingDialog!!.show()
    }

    private fun dismissLoding() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    /**
     * 页面跳转
     */
    fun getjumpActivity(address: String) {
        ARouter.getInstance().build(address).navigation()
    }


    fun showDialog(drawDot: String, drawLine: String, type: String = "") {
        if (drawDialog == null) {
            drawDialog = DrawDialog(mContext, R.style.AlertDialogStyle)
        }
        drawDialog!!.show()
        drawDialog?.setTextContent(drawDot, drawLine)
        drawDialog?.setCallBaceck(object : DrawDialog.OnClickEvent {
            override fun onTakingPicturesClick() {
                getTakingPicturesClick()
            }

            override fun onPhotoAlbumClick() {
                getPhotoAlbumClick()
            }
        })
    }

    /**
     * 是否已登录
     */
    fun getisFristLogin() {
        if (!SPUtils.getInstance().getString(Config.TOKEN)?.isNullOrBlank()!!) {
            startActivity(Intent(mContext, MainActivity::class.java))
        }
    }

    /**
     * 消息、事件接收回调
     */
    open fun handleEvent(msg: EventMessage) {
        if (msg.code == EventCode.LOGIN_OUT) {
            finish()
        }
    }

    /**
     * 接口请求错误回调
     */
    open fun errorResult(errorResult: ErrorResult) {}

    /**
     * 底部弹窗回调方法
     */
    open fun getTakingPicturesClick() {

    }

    /**
     * 底部弹窗回调方法
     */
    open fun getPhotoAlbumClick() {

    }

}