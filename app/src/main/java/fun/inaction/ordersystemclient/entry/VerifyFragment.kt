package `fun`.inaction.ordersystemclient.entry

import `fun`.inaction.ordersystemclient.MainActivity
import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.databinding.FragmentVerifyCodeBinding
import `fun`.inaction.ordersystemclient.network.OkCallback
import `fun`.inaction.ordersystemclient.network.ServiceCreator
import `fun`.inaction.ordersystemclient.network.bean.LoginResult
import `fun`.inaction.ordersystemclient.network.service.UserService
import `fun`.inaction.ordersystemclient.util.showToast
import android.content.Intent
import `fun`.inaction.ordersystemclient.network.bean.Result
import `fun`.inaction.ordersystemclient.util.UserBaseUtil
import `fun`.inaction.ordersystemclient.util.startActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hi.dhl.binding.viewbind

class VerifyFragment:Fragment(R.layout.fragment_verify_code) {

    private val binding by viewbind<FragmentVerifyCodeBinding>()

    private val args by navArgs<VerifyFragmentArgs>()

    private val userService = ServiceCreator.create<UserService>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.phoneTextView.text = args.tel
        // 发送验证码
        sendCaptcha(args.tel)
        // 开始倒计时
        binding.reGetButton.startCountDown()

        // "重新获取"点击事件
        binding.reGetButton.onClickReGet = {
            // 再发送验证码
            sendCaptcha(args.tel)
        }
        // 验证码输入框的输入完成监听
        binding.verifyEditText.setInputCompleteListener { _, captcha ->
            // 输入完成，发送登录/注册 网络请求
            login(args.tel,captcha)
        }
    }

    /**
     * 发送验证码
     */
    private fun sendCaptcha(tel:String){
        userService.getCaptcha(tel).enqueue(object : OkCallback<Result>() {
            override fun onSuccess(result: Result) {
                showToast("验证码发送成功！")
            }
        })
    }

    /**
     * 登录
     */
    private fun login(tel:String,captcha:String){
        userService.login(tel,captcha).enqueue(object : OkCallback<LoginResult>() {
            override fun onSuccess(result: LoginResult) {
                // 登录成功

                // 保存用户信息
                UserBaseUtil.onSuccessLogin(args.tel,result.userID)
                // 跳转界面
//                val intent = Intent(activity,MainActivity::class.java)
//                startActivity(intent)
//                activity?.finish()
//                findNavController().navigate(R.id.action_verifyFragment_to_main_graph)
                startActivity(ScanRestaurantIdActivity::class.java)
                activity?.finish()
            }
        })
    }

}