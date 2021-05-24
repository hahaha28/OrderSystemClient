package `fun`.inaction.ordersystemclient.adapter

import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.network.bean.Dish
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class SimpleDishAdapter:BaseQuickAdapter<Dish,BaseViewHolder>(R.layout.adapter_simple_dish_list) {

    override fun convert(holder: BaseViewHolder, item: Dish) {
        TODO("Not yet implemented")
    }

}