package `fun`.inaction.ordersystemclient.network.service

import `fun`.inaction.ordersystemclient.network.bean.DishGroup
import `fun`.inaction.ordersystemclient.network.bean.DishGroupResult
import `fun`.inaction.ordersystemclient.network.bean.GetAllDishResult
import retrofit2.Call
import retrofit2.http.GET

interface DishService {

    /**
     * 获取所有分组
     */
    @GET("/dish/group/get")
    fun getDishGroup(): Call<DishGroupResult>

    @GET("/dish/get/all")
    fun getAllDish():Call<GetAllDishResult>

}