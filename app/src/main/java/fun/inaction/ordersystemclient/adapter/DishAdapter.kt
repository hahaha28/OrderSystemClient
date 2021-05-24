package `fun`.inaction.ordersystemclient.adapter

import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.adapter.bean.DishSection
import `fun`.inaction.ordersystemclient.custom.view.PlusMinusBtn
import `fun`.inaction.ordersystemclient.network.ServiceCreator
import `fun`.inaction.ordersystemclient.network.bean.Dish
import `fun`.inaction.ordersystemclient.util.load
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class DishAdapter:BaseSectionQuickAdapter<DishSection,BaseViewHolder>
    (R.layout.adapter_dish_header,R.layout.adapter_dish_list) {

    val countList = LinkedHashMap<Int,Int>()

    var onTotalPriceChangeListener:(Float)->Unit = {}

    override fun convert(holder: BaseViewHolder, item: DishSection) {
//        Log.e("tag","position=${holder.adapterPosition}")
        val dish = item.data!!
        val imageView = holder.getView<ImageView>(R.id.dishImg)
        imageView.load("${ServiceCreator.BASE_URL}${dish.pic}")
        holder.setText(R.id.dishName,dish.name)
        holder.setText(R.id.salesText,"销量${dish.sales}")
        holder.setText(R.id.rateText,"好评率${dish.positiveRate}")
        holder.setText(R.id.priceText,"￥${dish.price}")

        val countBtn = holder.getView<PlusMinusBtn>(R.id.countBtn)
        if(countList.containsKey(holder.adapterPosition)){
            countBtn.setCount(countList[holder.adapterPosition]!!)
        }else{
            countBtn.setCount(0)
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        super.onItemViewHolderCreated(viewHolder, viewType)
        val countBtn =viewHolder.getViewOrNull<PlusMinusBtn>(R.id.countBtn)
        if(countBtn != null) {
            countBtn.onCountChangeListener = {
                val index = viewHolder.adapterPosition
                if(it == 0){
                    countList.remove(index)
                }else {
                    countList[index] = it
                }
                onTotalPriceChangeListener(calculatePrice())
            }
        }
    }


    override fun convertHeader(helper: BaseViewHolder, item: DishSection) {
        helper.setText(R.id.sectionTitle,item.sectionTitle)
    }

    fun calculatePrice():Float{
        var temp = 0f
        countList.keys.forEach {
           temp += data[it].data!!.price*countList[it]!!
        }
        return temp
    }

}