package `fun`.inaction.ordersystemclient.network

import okhttp3.Request
import `fun`.inaction.ordersystemclient.network.bean.Result
import retrofit2.Call
import retrofit2.Response

fun <T:Result> Call<T>.call(onOk:(T)->Unit){
    this.enqueue(object : OkCallback<T>() {
        override fun onSuccess(result: T) {
            onOk(result)
        }
    })
}

fun Request.toLogString():String{
    return "发送请求\n" +
            "${this.method()},${this.url()},cookie:${this.header("cookie")}\n" +
            "${this.body()?.toString()}"
}

fun <T> Response<T>.toLogString():String{
    return "获取响应\n" +
            "${this.code()}\n" +
            "${this.body()?.toString()}"
}