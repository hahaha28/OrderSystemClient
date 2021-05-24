package `fun`.inaction.ordersystemclient.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import `fun`.inaction.ordersystemclient.network.bean.Result

/**
 * 封装网络请求回调，将其分为各种情况
 */
open class OkCallback<T : Result> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.v("network",response.toLogString())
        if(response.isSuccessful){
            if(response.body()!!.code == 200){
                onSuccess(response.body()!!)
            }else{
                onError(response.body()!!.code,response.body()!!.msg,response.body()!!)
            }
        }else{
            onError(response.code(),"服务器错误",response.body())
            onFailureFinally()
        }
        onFinally()
    }

    /**
     * 网络请求错误时会调用此方法，code 不是200
     * 网络请求异常不调用此方法
     */
    open fun onError(code:Int,msg:String,data:T?){
        NetworkConfig.onError(code,msg,data)
    }

    /**
     * 网络请求成功时会调用此方法
     */
    open fun onSuccess(result: T){

    }

    /**
     * 网络不成功（不管是异常还是错误），最终一定会调用此方法
     */
    open fun onFailureFinally(){

    }

    /**
     * 不管成功还是失败，最终一定会调用此方法
     */
    open fun onFinally(){

    }

    /**
     * 网络请求异常时调用此方法
     */
    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailureFinally()
        onFinally()
        NetworkConfig.onFailure()
    }
}