package `fun`.inaction.ordersystemclient.custom.view

import `fun`.inaction.ordersystemclient.R
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout

class ProgressView(context: Context) : FrameLayout(context) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.progress_view,this,true)
    }

}