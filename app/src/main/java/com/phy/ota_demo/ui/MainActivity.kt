package com.phy.ota_demo.ui

import android.view.KeyEvent
import android.view.View
import com.phy.ota_demo.R
import com.phy.ota_demo.basic.PhyActivity
import com.phy.ota_demo.databinding.ActivityQrcodeStarterBinding
import com.phy.ota_demo.utils.FileUtil
import kotlin.properties.Delegates

class MainActivity : PhyActivity<ActivityQrcodeStarterBinding>() ,   View.OnClickListener{
    private lateinit var fileUtil: FileUtil

    override fun onCreate() {
        fileUtil = FileUtil.getInstance(this)

        // 设置点击事件
        binding.btnStartQrcode.setOnClickListener(this)
        binding.btnScanQrcodeByText.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_start_qrcode -> {             // 跳转到扫描二维码界面
                jumpActivityFinish(QrcodeScanerActivity::class.java)
                finish()
            }
            R.id.btn_scan_qrcode_byText -> {
                jumpActivityFinish(QrcodeTextInputActivity::class.java)
                finish()
            }

        }
    }

    private var timeMillis by Delegates.notNull<Long>()

    /**
     * Add a prompt to exit the application
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                showMsg("再次按下退出应用程序")
                timeMillis = System.currentTimeMillis()
            } else {
                exitTheProgram()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}