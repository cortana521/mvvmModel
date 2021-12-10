package com.dzf.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.dzf.mvvm.R
import com.dzf.mvvm.api.error.ErrorResult
import com.dzf.mvvm.dialog.DrawDialog
import com.dzf.mvvm.dialog.LodingDialog
import com.dzf.mvvm.event.EventMessage
import com.dzf.mvvm.utils.GenericParadigmUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


abstract class BaseFragment<VM : BaseViewModel<VB>, VB : ViewBinding> : Fragment() {

    lateinit var mActivity: FragmentActivity
    var contentView: View? = null
    lateinit var vm: VM
    lateinit var vb: VB
    private var loadingDialog: LodingDialog? = null
    var drawDialog: DrawDialog? = null

    //Fragment的View加载完毕的标记
    private var isViewCreated = false

    //Fragment对用户可见的标记
    private var isUIVisible = false
    var isVisibleToUser = false

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = context as FragmentActivity
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

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == contentView) {
            contentView = vb.root
            init()
            initView()
            initClick()
            initData()
            LogUtils.e(getClassName())
        }
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lazyLoad()
    }

    private fun init() {
        EventBus.getDefault().register(this)
        //loading
        vm.isShowLoading.observe(this, Observer {
            if (it) showLoading() else dismissLoding()
        })
        //错误信息
        vm.errorData.observe(this, Observer {
            if (it.show) ToastUtils.showShort( it.errMsg)
            errorResult(it)
        })
    }

    fun showDialog(drawDot:String,drawLine:String) {
        if (drawDialog == null) {
            drawDialog = DrawDialog(mActivity, R.style.AlertDialogStyle)
        }
        drawDialog!!.show()
        drawDialog?.setTextContent(drawDot,drawLine)
        drawDialog?.setCallBaceck(object : DrawDialog.OnClickEvent {
            override fun onTakingPicturesClick() {
                getPermission(PermissionConstants.CAMERA)
            }

            override fun onPhotoAlbumClick() {
                getPermission(PermissionConstants.STORAGE)
            }
        })
    }

    private fun getPermission(permision: String) {
        PermissionUtils.permissionGroup(permision)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d("onGranted---$permissionsGranted")
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        LogUtils.d("permissionsDeniedForever.isNotEmpty()---${permissionsDeniedForever.isEmpty()}")
                    } else {
                        LogUtils.d("permissionsDeniedForever.isNotEmpty()---${permissionsDeniedForever.isEmpty()}")
                    }
                }
            })
            .theme { activity -> ScreenUtils.setFullScreen(activity) }
            .request()
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
        val className = "BaseFragment"
        try {
            return javaClass.name
        } catch (e: Exception) {
        }
        return className
    }

    abstract fun initView()

    abstract fun initClick()

    abstract fun initData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            lazyLoadData()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false
            isUIVisible = false
        }
    }

    //需要懒加载的数据，重写此方法
    abstract fun lazyLoadData()

    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LodingDialog(mActivity, R.style.LoadingDialog)
        }
        loadingDialog!!.show()
    }

    private fun dismissLoding() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    /**
     * 消息、事件接收回调
     */
    open fun handleEvent(msg: EventMessage) {}

    /**
     * 接口请求错误回调
     */
    open fun errorResult(errorResult: ErrorResult) {}

    override fun onDestroyView() {
        super.onDestroyView()
        contentView = null
    }
}
