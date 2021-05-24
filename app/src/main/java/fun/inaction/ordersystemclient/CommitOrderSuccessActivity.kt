package `fun`.inaction.ordersystemclient

import `fun`.inaction.ordersystemclient.databinding.ActivityCommitOrderSuccessBinding
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hi.dhl.binding.viewbind

class CommitOrderSuccessActivity : AppCompatActivity() {

    private val binding by viewbind<ActivityCommitOrderSuccessBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val takeFoodCode = intent.getStringExtra("takeFoodCode")
        binding.takeFoodCode.text = takeFoodCode

    }

    companion object{
        fun startActivity(activity:Activity,takeFoodCode:String){
            val intent = Intent(activity,CommitOrderSuccessActivity::class.java)
            intent.putExtra("takeFoodCode",takeFoodCode)
            activity.startActivity(intent)
        }
    }

}