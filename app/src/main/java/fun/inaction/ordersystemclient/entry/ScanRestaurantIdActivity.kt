package `fun`.inaction.ordersystemclient.entry

import `fun`.inaction.ordersystemclient.MainActivity
import `fun`.inaction.ordersystemclient.databinding.ActivityScanRestaurantIdBinding
import `fun`.inaction.ordersystemclient.util.UserBaseUtil
import `fun`.inaction.ordersystemclient.util.logi
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hi.dhl.binding.viewbind
import com.kongzue.dialog.v3.MessageDialog
import com.permissionx.guolindev.PermissionX
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils


class ScanRestaurantIdActivity : AppCompatActivity() {

    private val TAG = "ScanRestaurantIdActivity"

    private val binding by viewbind<ActivityScanRestaurantIdBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 检查是否已有商家id
        if (UserBaseUtil.getRestaurantID() != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.scanBtn.setOnClickListener {
            // 申请权限
            PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA)
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        val intent = Intent(this, CaptureActivity::class.java)
                        startActivityForResult(intent, 1)
                    }
                }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            data?.let {
                data.extras?.let { bundle ->

                    if (bundle.getInt(CodeUtils.RESULT_TYPE) === CodeUtils.RESULT_SUCCESS) {
                        val result = bundle.getString(CodeUtils.RESULT_STRING)
                        logi(TAG,"扫码结果:${result}")
                        if (result == null || !result.startsWith("restaurantID:") ) {
                            MessageDialog.show(this, "提示", "二维码错误！")
                        } else {
                            // 成功
                            val restaurantID = result.substringAfter("restaurantID:")
                            Log.e("tag", "扫描二维码成功：restaurantID=${restaurantID}")
                            UserBaseUtil.setRestaurantID(restaurantID)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        MessageDialog.show(this, "提示", "二维码解析错误！")
                    }
                }
            }
        }
    }
}