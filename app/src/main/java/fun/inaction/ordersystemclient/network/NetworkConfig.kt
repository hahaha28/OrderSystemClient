package `fun`.inaction.ordersystemclient.network

import `fun`.inaction.ordersystemclient.network.bean.Result

object NetworkConfig {

    /**
     * 网络请求错误的回调（服务器返回数据，但请求失败，如登录失败等情况）
     */
    var onError: (Int, String, Result?)->Unit = { _, _, _ -> }

    /**
     * 网络请求异常的统一回调（没有网，服务器错误）
     */
    var onFailure : ()->Unit = {}

    /**
     * 获取UserID，用来添加cookie
     */
    var getUserID: ()->String = { "" }

    /**
     * 获取商家ID，用来添加cookie
     */
    var getRestaurantID: ()->String = {""}

}