package `fun`.inaction.ordersystemclient.entry

import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.databinding.FragmentLoginBinding
import `fun`.inaction.ordersystemclient.util.isPhoneNumber
import `fun`.inaction.ordersystemclient.util.showToast
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hi.dhl.binding.viewbind

class LoginFragment:Fragment(R.layout.fragment_login) {

    private val binding by viewbind<FragmentLoginBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        activity?.window?.statusBarColor = Color.WHITE


        // 设置toolbar
        (activity as AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // editText 获取焦点
        binding.phoneEditText.requestFocus()

        // 下一步 点击事件
        binding.nextButton.setOnClickListener {
            // 检查手机号是否合法
            val tel = binding.phoneEditText.text.toString()
            if(!tel.isPhoneNumber()){
                showToast("请输入合法手机号",Toast.LENGTH_LONG)
                return@setOnClickListener
            }
            val action = LoginFragmentDirections.actionLoginFragmentToVerifyFragment(tel)
            findNavController().navigate(action)
        }
    }



}