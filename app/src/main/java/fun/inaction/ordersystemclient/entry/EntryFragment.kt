package `fun`.inaction.ordersystemclient.entry

import `fun`.inaction.ordersystemclient.MainActivity
import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.databinding.FragmentEntryBinding
import `fun`.inaction.ordersystemclient.util.*
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hi.dhl.binding.viewbind

class EntryFragment: Fragment(R.layout.fragment_entry) {

    private val binding by viewbind<FragmentEntryBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        setStatusBarColor(activity?.window, getColor(R.color.themeColor))
//        setDarkStatusBar(activity?.window)

        if(UserBaseUtil.isLogin()){
            startActivity(ScanRestaurantIdActivity::class.java)
            activity?.finish()
        }else{
            binding.loginButton.show()
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_loginFragment)
//            setStatusBarColor(activity?.window, Color.WHITE)
//            setLightStatusBar(activity?.window)
        }
    }
}