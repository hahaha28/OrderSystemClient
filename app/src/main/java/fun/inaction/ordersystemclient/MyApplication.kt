package `fun`.inaction.ordersystemclient

import `fun`.inaction.ordersystemclient.network.NetworkConfig
import `fun`.inaction.ordersystemclient.util.SDK_APP_ID
import `fun`.inaction.ordersystemclient.util.UserBaseUtil
import `fun`.inaction.ordersystemclient.util.loge
import `fun`.inaction.ordersystemclient.util.showToast
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kongzue.dialog.util.DialogSettings
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMSDKConfig
import com.tencent.imsdk.v2.V2TIMSDKListener
import com.tencent.mmkv.MMKV
import com.uuzuche.lib_zxing.activity.ZXingLibrary

class MyApplication:Application() {



    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
        MMKV.initialize(this)

        configNetwork()

        // 配置对话框
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS

        // 初始化腾讯IM SDK
        initIMSDK()

        // 初始化二维码扫描
        ZXingLibrary.initDisplayOpinion(this)
    }

    /**
     * 配置网络
     */
    private fun configNetwork(){
        NetworkConfig.onError = { code, msg, result ->
            showToast("Error! code:${code},msg:${msg}", Toast.LENGTH_LONG)
            loge("Network","错误${code},${msg},${result}")
        }
        NetworkConfig.getRestaurantID = {
            UserBaseUtil.getRestaurantID()?:""
//            "6073fee28a458e45e88bb178"
        }
//        UserBaseUtil.setRestaurantID("6073fee28a458e45e88bb178")
        NetworkConfig.getUserID = {
            UserBaseUtil.getUserID()?:""
        }
    }

    private fun initIMSDK() {
        val config = V2TIMSDKConfig()
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO)
        V2TIMManager.getInstance().initSDK(context, SDK_APP_ID, config,
            object : V2TIMSDKListener() {
                override fun onConnecting() {
                    super.onConnecting()
                    // 正在连接腾讯云服务器
                    Log.e("tag","正在连接腾讯云服务器")
                }

                override fun onConnectSuccess() {
                    super.onConnectSuccess()
                    // 成功连接腾讯云服务器
                    Log.e("tag","成功连接腾讯云服务器")
                }

                override fun onConnectFailed(code: Int, error: String?) {
                    super.onConnectFailed(code, error)
                    // 连接腾讯云服务器失败
                    Log.e("tag","连接腾讯云服务器失败")
                }

                override fun onUserSigExpired() {
                    super.onUserSigExpired()
                    // 登录票据过期，需要新的UserSig
                    Log.e("tag","登录票据过期，需要新的UserSig")
                }
            })
    }

    companion object {
        private lateinit var _context: Context
        public val context: Context
            get() = _context
    }
}