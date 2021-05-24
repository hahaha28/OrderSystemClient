package `fun`.inaction.ordersystemclient.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CookieInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("cookie","userID=${NetworkConfig.getRestaurantID()};" +
                    "customerID=${NetworkConfig.getUserID()}")
            .build()
        return chain.proceed(newRequest)
    }
}