package `fun`.inaction.ordersystemclient.viewmodel

import `fun`.inaction.ordersystemclient.cache.MainCache
import `fun`.inaction.ordersystemclient.network.ServiceCreator
import `fun`.inaction.ordersystemclient.network.bean.DishGroup
import `fun`.inaction.ordersystemclient.network.call
import `fun`.inaction.ordersystemclient.network.service.DishService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

//    val dishListData = MutableLiveData<MutableList<Dish>>()
    val dishGroupData = MutableLiveData<MutableList<DishGroup>>()
    private val service = ServiceCreator.create<DishService>()

    init {
        // 获取缓存
        MainCache.getDishCacheData()?.let {
            dishGroupData.value = it
        }
        // 发网路请求
        requestDishData()
//        requestDishGroupData()
    }

    /**
     * 请求所有菜品及分组数据
     */
    private fun requestDishData(){
        service.getAllDish().call {
            dishGroupData.value = it.dishData
            // 缓存数据
            MainCache.cacheDishData(it.dishData)
        }
    }

    /**
     * 请求分组数据
     */
    private fun requestDishGroupData(){
        service.getDishGroup().call{
            dishGroupData.value = it.dishGroups
        }
    }
}