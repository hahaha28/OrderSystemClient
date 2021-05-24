package `fun`.inaction.ordersystemclient.adapter

import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.network.bean.DishGroup
import `fun`.inaction.ordersystemclient.util.getColor
import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class DishGroupAdapter:BaseQuickAdapter<DishGroup,BaseViewHolder>(R.layout.adapter_group_list) {

    var selectedIndex = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun convert(holder: BaseViewHolder, item: DishGroup) {
        val textView = holder.getView<TextView>(R.id.groupName)
        textView.text = item.name

        if(holder.adapterPosition == selectedIndex){
            holder.itemView.setBackgroundColor(Color.WHITE)
            textView.setTextColor(getColor(R.color.colorText))
        }else{
            holder.itemView.setBackgroundColor(getColor(R.color.grayBg))
            textView.setTextColor(getColor(R.color.colorTextLight))
        }

    }

}