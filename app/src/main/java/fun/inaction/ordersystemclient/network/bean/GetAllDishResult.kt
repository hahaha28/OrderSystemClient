package `fun`.inaction.ordersystemclient.network.bean

data class GetAllDishResult(
    var dishData:MutableList<DishGroup>
):Result()
