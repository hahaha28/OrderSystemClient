package `fun`.inaction.ordersystemclient.custom.view

import `fun`.inaction.ordersystemclient.R
import `fun`.inaction.ordersystemclient.util.hide
import `fun`.inaction.ordersystemclient.util.show
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.math.min

class PlusMinusBtn(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val plusBtn:FrameLayout
    private val minusBtn:FrameLayout
    private val numText:TextView
    private var num:Int = 0

    var onCountChangeListener:(Int)->Unit = {}

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.custom_view_plus_minus_btn,this,true)
        plusBtn = findViewById(R.id.plusBtn)
        minusBtn = findViewById(R.id.minusBtn)
        numText = findViewById(R.id.numText)

        plusBtn.setOnClickListener {
            num++
            numText.text = "$num"
            if(num == 1){
                minusBtn.show()
                numText.show()
            }
            onCountChangeListener(num)
        }

        minusBtn.setOnClickListener {
            num --
            numText.text = "$num"
            if(num == 0){
                minusBtn.hide()
                numText.hide()
            }
            onCountChangeListener(num)
        }
    }

    fun getCount():Int = num

    fun setCount(num:Int){
        if(num <= 0){
            this.num = 0
            minusBtn.hide()
            numText.hide()
        }else{
            this.num = num
            numText.text = "$num"
            minusBtn.show()
            numText.show()
        }
        onCountChangeListener(num)
    }

}