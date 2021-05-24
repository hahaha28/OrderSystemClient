package `fun`.inaction.ordersystemclient.util

import com.tencent.mmkv.MMKV

object UserBaseUtil {

    private val TAG = "UserBaseUtil"

    private val kv = MMKV.defaultMMKV()!!

    fun onSuccessLogin(tel:String,id:String,name:String=""){
        kv.encode("userTel",tel)
        kv.encode("userID",id)
//        kv.encode("userName",name)
    }

    fun isLogin():Boolean{
        val userID = kv.getString("userID",null)
        logi(TAG,"userID : ${userID}")
        return  userID != null
    }

    /**
     * 退出登录
     */
    fun logout(){
        kv.remove("userID")
        kv.remove("userTel")
        kv.remove("restaurantID")
//        kv.remove("userName")
    }

    /**
     * 切换商家
     */
    fun switchRestaurant(){
        kv.remove("restaurantID")
    }

    fun setRestaurantID(id:String){
        kv.encode("restaurantID",id)
    }

    fun getUserID():String? = kv.getString("userID",null)

    fun getRestaurantID():String? = kv.getString("restaurantID",null)

    fun getUserName():String? = kv.getString("userName",null)

}