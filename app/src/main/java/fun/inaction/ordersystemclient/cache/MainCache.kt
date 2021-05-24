package `fun`.inaction.ordersystemclient.cache

import `fun`.inaction.ordersystemclient.network.bean.DishGroup
import `fun`.inaction.ordersystemclient.util.DiskCacheUtil

object MainCache {

    fun cacheDishData(dishData:List<DishGroup>){
        DiskCacheUtil.writeObj("mainDishData",dishData)
    }

    fun getDishCacheData():MutableList<DishGroup>?{
        return DiskCacheUtil.getObj<MutableList<DishGroup>>("mainDishData")
    }

}