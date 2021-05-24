package `fun`.inaction.ordersystemclient

import `fun`.inaction.ordersystemclient.viewmodel.MainViewModel
import `fun`.inaction.ordersystemclient.adapter.DishAdapter
import `fun`.inaction.ordersystemclient.adapter.DishGroupAdapter
import `fun`.inaction.ordersystemclient.adapter.bean.DishSection
import `fun`.inaction.ordersystemclient.custom.view.ProgressView
import `fun`.inaction.ordersystemclient.databinding.ActivityMainBinding
import `fun`.inaction.ordersystemclient.entry.EntryActivity
import `fun`.inaction.ordersystemclient.entry.ScanRestaurantIdActivity
import `fun`.inaction.ordersystemclient.network.OkCallback
import `fun`.inaction.ordersystemclient.network.ServiceCreator
import `fun`.inaction.ordersystemclient.network.bean.Order
import `fun`.inaction.ordersystemclient.network.bean.OrderDish
import `fun`.inaction.ordersystemclient.network.service.OrderService
import `fun`.inaction.ordersystemclient.util.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hi.dhl.binding.viewbind
import com.kongzue.dialog.v3.MessageDialog
import com.kongzue.dialog.v3.TipDialog
import com.kongzue.dialog.v3.WaitDialog
import com.tencent.imsdk.v2.*
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private val binding by viewbind<ActivityMainBinding>()

    private val viewModel by viewModels<MainViewModel>()

    private val dishAdapter = DishAdapter()
    private val dishAdapterLayoutManager = LinearLayoutManager(this)
    private val dishGroupAdapter = DishGroupAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置toolbar
        setSupportActionBar(binding.toolbar2)

        // 登录IM
        loginIM()

        // 设置RecyclerView
        binding.dishRecyclerView.layoutManager = dishAdapterLayoutManager
        binding.dishRecyclerView.adapter = dishAdapter
        binding.groupRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.groupRecyclerView.adapter = dishGroupAdapter
//        dishAdapter.setEmptyView(ProgressView(this))

        // 监听菜品数据变化
//        viewModel.dishListData.observe(this){
//            dishAdapter.setNewInstance(it)
//        }

        // 监听分组数据变化
        viewModel.dishGroupData.observe(this) {
//            val groupList = mutableListOf<>()
            binding.progressView.hide()
            dishGroupAdapter.setNewInstance(it)
            val dishData = mutableListOf<DishSection>()
            it.forEach { dishGroup ->
                dishData.add(DishSection(dishGroup.name))
                dishGroup.dishList.forEach { dish ->
                    dishData.add(DishSection(dish))
                }
            }
            dishAdapter.setNewInstance(dishData)
        }

        // 分组点击事件
        dishGroupAdapter.setOnItemClickListener { adapter, view, position ->
            dishGroupAdapter.selectedIndex = position

            val groupData = viewModel.dishGroupData.value!!
            var item = 0
            for (i in 0 until position) {
                item += groupData[i].dishIDs.size + 1
            }

            dishAdapterLayoutManager.scrollToPositionWithOffset(item, 0)
        }

        // recyclerView 滚动监听
        binding.dishRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val position = dishAdapterLayoutManager.findFirstVisibleItemPosition()
                val dishGroup = viewModel.dishGroupData.value!!
                var temp = 0
                var index = 0
                dishGroup.forEach {
                    if (temp == position) {
                        dishGroupAdapter.selectedIndex = index
                        return@forEach
                    }
                    if (temp > position) {
                        return@forEach
                    }
                    temp += it.dishList.size
                    index++
                }
            }
        })

        // 总价格变化监听
        dishAdapter.onTotalPriceChangeListener = {
            binding.totalCost.text = String.format("%.2f", it)
        }

        // 结算按钮 点击事件
        binding.commitBtn.setOnClickListener {
//            Log.e("tag","size = ${dishAdapter.countList.keys.size}")
            if(dishAdapter.countList.keys.size == 0){
                TipDialog.show(this,"未选择商品",TipDialog.TYPE.WARNING)
                    .setTipTime(2000)
                return@setOnClickListener
            }
            MessageDialog.show(this,"提示","是否确认提交订单？","确定","取消")
                .setOnOkButtonClickListener { baseDialog, v ->
                    // 提交订单
                    commitOrder()
                    baseDialog.doDismiss()
                    true
                }
        }

    }

    /**
     * 提交订单
     */
    private fun commitOrder(){

        // 等待对话框
        WaitDialog.show(this,"请稍后...")
        // 发送请求提交订单
        val orderService = ServiceCreator.create<OrderService>()
        val order = Order(
            customerID = UserBaseUtil.getUserID()!!,
            restaurantID = UserBaseUtil.getRestaurantID()!!,
            dishes = getOrderDishes(),
            cost = dishAdapter.calculatePrice(),
        )
        orderService.commitOrder(order).enqueue(object : OkCallback<Order>() {
            override fun onSuccess(result: Order) {
                super.onSuccess(result)
                // 发送IM消息给餐厅
                val msg = "newOrder:${Gson().toJson(result)}"
                V2TIMManager.getInstance().sendC2CCustomMessage(msg.toByteArray(),
                    UserBaseUtil.getRestaurantID(),
                    object : V2TIMValueCallback<V2TIMMessage> {
                        override fun onError(code: Int, desc: String?) {
                            // 订单提交失败
                            WaitDialog.dismiss()
                            MessageDialog.show(this@MainActivity,"错误","订单提交失败!\n${code},${desc}")
                        }

                        override fun onSuccess(t: V2TIMMessage?) {
                            // 订单提交成功
                            WaitDialog.dismiss()
                            CommitOrderSuccessActivity.startActivity(this@MainActivity, result.takeFoodCode!!)
                        }
                    })

            }

            override fun onFailureFinally() {
                super.onFailureFinally()
                // 订单提交失败
                WaitDialog.dismiss()
                TipDialog.show(this@MainActivity, "订单提交失败！", TipDialog.TYPE.ERROR)
                    .setTipTime(2000)

            }
        })
    }

    /**
     * 登录即时通信系统
     */
    private fun loginIM(){
        V2TIMManager.getInstance().login(UserBaseUtil.getUserID()!!,
            GenerateUserSigUtil.genTestUserSig(UserBaseUtil.getUserID()!!),
            object : V2TIMCallback {
                override fun onError(code: Int, desc: String?) {
                    // 登录失败
//                    callback(false)
                    TipDialog.show(this@MainActivity,"IM登录失败，请重启应用\n${desc}",TipDialog.TYPE.ERROR)
                }

                override fun onSuccess() {
                    // 登录成功
                    Log.e("tag","IM登录成功,id:${UserBaseUtil.getUserID()}")
                    setIMMsgListener()
                }
            })
    }

    /**
     * 设置IM消息监听
     */
    private fun setIMMsgListener(){
        V2TIMManager.getInstance().addSimpleMsgListener(object : V2TIMSimpleMsgListener() {
            override fun onRecvC2CCustomMessage(
                msgID: String?,
                sender: V2TIMUserInfo?,
                customData: ByteArray?
            ) {
                customData?.let{
                    val msg = it.decodeToString()
                    Log.e("tag","收到消息：$msg")
                    if(msg.startsWith("takeFood:")){
                        // 取餐
                        val data = msg.substringAfter("takeFood:")
                        val jsonObj = JSONObject(data)
                        // 获取取餐码
                        val code = jsonObj.getString("code")
                        NotificationUtil.sendTakeFoodNotification(code)
                    }
                }
            }
        })
    }

    private fun getOrderDishes():MutableList<OrderDish>{
        val dishes = mutableListOf<OrderDish>()
        val adapterData = dishAdapter.data
        dishAdapter.countList.keys.forEach { key ->
            val data = adapterData[key]
            dishes.add(OrderDish(data.data!!._id!!,dishAdapter.countList[key]!!))
        }
        return dishes
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            // 我的订单
            R.id.orderList -> {
//                NotificationUtil.sendTakeFoodNotification("111")
                val intent = Intent(this,OrderHistoryActivity::class.java)
                startActivity(intent)
            }

            // 反馈
            R.id.report -> {
                val intent = Intent(this,SuggestActivity::class.java)
                startActivity(intent)
            }

            // 切换商家
            R.id.switchRestaurant -> {
                UserBaseUtil.switchRestaurant()
                DiskCacheUtil.clearAllCache()
                startActivity(ScanRestaurantIdActivity::class.java)
                finish()
            }

            // 退出登录
            R.id.exitItem -> {
                UserBaseUtil.logout()
                startActivity(EntryActivity::class.java)
                finish()
            }
        }
        return true
    }
}