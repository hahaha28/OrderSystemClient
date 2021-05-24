package `fun`.inaction.ordersystemclient

import `fun`.inaction.ordersystemclient.adapter.OrderListAdapter
import `fun`.inaction.ordersystemclient.databinding.ActivityOrderHistoryBinding
import `fun`.inaction.ordersystemclient.network.ServiceCreator
import `fun`.inaction.ordersystemclient.network.call
import `fun`.inaction.ordersystemclient.network.service.OrderService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hi.dhl.binding.viewbind

class OrderHistoryActivity : AppCompatActivity() {

    private val binding by viewbind<ActivityOrderHistoryBinding>()

    private val adapter = OrderListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置recyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // 请求数据
        requestHistoryOrder()
    }

    fun requestHistoryOrder(){
        val service = ServiceCreator.create<OrderService>()
        service.getHistoryOrder().call {
            adapter.setNewInstance(it.orders)
        }
    }

}