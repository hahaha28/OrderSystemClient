package `fun`.inaction.ordersystemclient.network.bean

data class HistoryOrderResult(
    var orders:MutableList<Order>
):Result() {
}