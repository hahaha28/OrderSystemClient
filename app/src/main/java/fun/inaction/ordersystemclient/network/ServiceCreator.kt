package `fun`.inaction.ordersystemclient.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    const val BASE_URL = "http://inaction.fun:22222"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(CookieInterceptor())
        .addInterceptor(RequestLogInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}