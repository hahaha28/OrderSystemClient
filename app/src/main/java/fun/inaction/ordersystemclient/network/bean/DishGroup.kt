package `fun`.inaction.ordersystemclient.network.bean


data class DishGroup(
    var name:String,
    var userID: String,
    var dishIDs:MutableList<String> = mutableListOf()
) {
    var _id:String? = null
    var dishList:MutableList<Dish> = mutableListOf()


}