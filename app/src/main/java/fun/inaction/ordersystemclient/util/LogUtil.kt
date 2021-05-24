package `fun`.inaction.ordersystemclient.util

import android.util.Log

val logControl = arrayOf(1,1,1,1,1)

fun logv(tag:String,msg:String){
    if(logControl[0] == 1) {
        Log.v(tag, msg)
    }
}

fun logd(tag:String,msg:String){
    if(logControl[1] == 1){
        Log.d(tag, msg)
    }
}

fun logi(tag: String,msg: String){
    if(logControl[2] == 1){
        Log.i(tag,msg)
    }
}

fun logw(tag:String,msg: String){
    if(logControl[3] == 1){
        Log.w(tag, msg)
    }
}

fun loge(tag: String,msg: String){
    if(logControl[4] == 1){
        Log.e(tag, msg)
    }
}