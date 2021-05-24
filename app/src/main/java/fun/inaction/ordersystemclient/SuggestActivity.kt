package `fun`.inaction.ordersystemclient

import `fun`.inaction.ordersystemclient.databinding.ActivitySuggestBinding
import `fun`.inaction.ordersystemclient.network.OkCallback
import `fun`.inaction.ordersystemclient.network.ServiceCreator
import `fun`.inaction.ordersystemclient.network.bean.Suggestion
import `fun`.inaction.ordersystemclient.network.call
import `fun`.inaction.ordersystemclient.network.service.SuggestionService
import `fun`.inaction.ordersystemclient.util.UserBaseUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.hi.dhl.binding.viewbind
import com.kongzue.dialog.v3.MessageDialog
import com.kongzue.dialog.v3.TipDialog
import com.kongzue.dialog.v3.WaitDialog
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMValueCallback

class SuggestActivity : AppCompatActivity() {

    private val binding by viewbind<ActivitySuggestBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.commitBtn.setOnClickListener {
            val text = binding.suggestText.text.toString()
            if(text == ""){
                TipDialog.show(this,"不能为空",TipDialog.TYPE.WARNING)
                    .setTipTime(1000)
                return@setOnClickListener
            }
            WaitDialog.show(this,"请稍后...")
            // 发送给服务器
            ServiceCreator.create<SuggestionService>().commitSuggestion(text).enqueue(object :
                OkCallback<Suggestion>() {
                override fun onSuccess(result: Suggestion) {
                    super.onSuccess(result)
                    // 发送IM消息给餐厅
                    val msg = "newSuggestion:${Gson().toJson(result)}"
                    V2TIMManager.getInstance().sendC2CCustomMessage(msg.toByteArray(),
                        UserBaseUtil.getRestaurantID(),
                        object : V2TIMValueCallback<V2TIMMessage> {

                            override fun onSuccess(t: V2TIMMessage?) {
                                // 发送成功
                                WaitDialog.dismiss()
                                finish()
                            }

                            override fun onError(code: Int, desc: String?) {
                                TipDialog.show(this@SuggestActivity,"失败:${desc}",TipDialog.TYPE.ERROR)
                                    .setTipTime(4000)
                            }
                        })
                }

                override fun onFailureFinally() {
                    super.onFailureFinally()
                    WaitDialog.dismiss()
                }
            })

        }

    }
}