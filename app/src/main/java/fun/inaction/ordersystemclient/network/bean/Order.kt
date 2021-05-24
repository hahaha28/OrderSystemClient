package `fun`.inaction.ordersystemclient.network.bean


data class Order(
    var _id:String? = null,
    var customerID:String,
    var restaurantID:String,
    var dishes:MutableList<OrderDish>,
    /**
     * 总价格
     */
    var cost:Float,
    /**
     * 取餐码
     */
    var takeFoodCode:String? = null,
    var timestamp :Long = 0,
    var status:Int = -1, // 状态，0已完成，1进行中
):Result()

data class OrderDish(
    var dishID:String,
    var num:Int
)
