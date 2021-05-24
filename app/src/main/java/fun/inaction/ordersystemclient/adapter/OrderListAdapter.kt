package `fun`.inaction.ordersystemclient.adapter

import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.network.bean.Order
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

class OrderListAdapter :BaseQuickAdapter<Order,BaseViewHolder>(R.layout.adapter_order_list) {


    private val dateFormat = SimpleDateFormat("M月d号 H:m")

    override fun convert(holder: BaseViewHolder, item: Order) {
        holder.setText(R.id.takeFoodCodeText,item.takeFoodCode)
        holder.setText(R.id.time,dateFormat.format(Date(item.timestamp)))
        when(item.status){
            0 ->         holder.setText(R.id.statusText,"已完成")
            1 ->         holder.setText(R.id.statusText,"未完成")
        }
    }

}