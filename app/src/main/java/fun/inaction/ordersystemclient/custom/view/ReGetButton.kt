package `fun`.inaction.ordersystemclient.custom.view

import `fun`.inaction.ordersystemclient.R
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.*

class ReGetButton(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val reGetTextView: TextView

    /**
     * 当前倒计时剩余时间，单位为s
     */
    private var curTime = 60;

    /**
     * 标记是否可以重新获取
     */
    private var canReGet = false

    private val myHandler = Handler(Looper.myLooper()!!)

    /**
     * “重新获取”的点击事件
     */
    var onClickReGet:()->Unit = {}


    init {
        val view  = LayoutInflater.from(context).inflate(R.layout.reget,this)
        reGetTextView = view.findViewById(R.id.regetTextView)
        reGetTextView.setOnClickListener {
            if(canReGet){
                canReGet = false
                reGetTextView.setTextColor(ContextCompat.getColor(context,R.color.colorTextLight))
                curTime = 60
                startCountDown()
                onClickReGet()
            }
        }
    }

    /**
     * 开始倒计时 60s
     */
    fun startCountDown(){
        curTime--
        if(curTime == 0){
            myHandler.post {
                canReGet()
            }
        }else{
            myHandler.post {
                reGetTextView.text = "${curTime}s"
            }
            val timer = Timer()
            timer.schedule(object: TimerTask() {
                override fun run() {
                    startCountDown()
                }

            },1000)
        }

    }

    private fun canReGet(){
        canReGet = true
        reGetTextView.text = "重新获取"
        reGetTextView.setTextColor(Color.parseColor("#1E6EFF"))
    }

}