package `fun`.inaction.ordersystemclient.network

import `fun`.inaction.ordersystemclient.network.toLogString
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestLogInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.v("network",request.toLogString())
        return chain.proceed(request)
    }
}