package `fun`.inaction.ordersystemclient.adapter.bean

import `fun`.inaction.ordersystemclient.network.bean.Dish
import com.chad.library.adapter.base.entity.SectionEntity

 class DishSection:SectionEntity {

     constructor(title:String){
         sectionTitle = title
         isHeader = true
     }

     constructor(dish:Dish){
         data = dish
         isHeader = false
     }

     var sectionTitle:String = ""

     var data:Dish? = null

    override var isHeader: Boolean = false
}
