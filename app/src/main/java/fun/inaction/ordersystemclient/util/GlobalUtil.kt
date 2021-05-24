package `fun`.inaction.ordersystemclient.util

import `fun`.inaction.ordersystemclient.MyApplication
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat

fun showToast(msg:String, length:Int = Toast.LENGTH_SHORT){
    Toast.makeText(MyApplication.context,msg,length).show()
}

fun setStatusBarColor(window:Window?,color:Int){
    window?.statusBarColor = color
}

fun setLightStatusBar(window:Window?){
    window?.decorView?.systemUiVisibility?.let {
        window.decorView.systemUiVisibility = it or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun setDarkStatusBar(window:Window?){

    window?.decorView?.systemUiVisibility?.let {
        window.decorView.systemUiVisibility = it and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}

fun getColor(id:Int):Int{
    return ContextCompat.getColor(MyApplication.context,id)
}