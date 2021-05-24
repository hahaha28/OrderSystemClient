package `fun`.inaction.ordersystemclient.network.service

import `fun`.inaction.ordersystemclient.network.bean.HistoryOrderResult
import `fun`.inaction.ordersystemclient.network.bean.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {

    @POST("/order/commit")
    fun commitOrder(@Body order:Order):Call<Order>

    @GET("/order/customer/get")
    fun getHistoryOrder():Call<HistoryOrderResult>

}