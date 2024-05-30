package com.phy.ota_demo.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import com.ailiwean.core.Result
import com.ailiwean.core.view.style2.NBZxingView
import com.ailiwean.core.zxing.ScanTypeConfig
import com.phy.ota_demo.basic.PhyActivity
import com.phy.ota_demo.ui.QrcodeResultActivity

class CusZxingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, def: Int = 0) : NBZxingView(context, attributeSet, def) {

    override fun resultBack(result: Result) {

        if (result.text.contains("=")){
            val value = result.text.split("=")[1]
            val intent = Intent(context , QrcodeResultActivity::class.java)
            intent.putExtra("data", value)
            intent.putExtra("type", 1)
            context.startActivity(intent)
        }
    }

    /***
     * 返回扫码类型
     * 1 ScanTypeConfig.HIGH_FREQUENCY 高频率格式(默认)
     * 2 ScanTypeConfig.ALL  所有格式
     * 3 ScanTypeConfig.ONLY_QR_CODE 仅QR_CODE格式
     * 4 ScanTypeConfig.TWO_DIMENSION 所有二维码格式
     * 5 ScanTypeConfig.ONE_DIMENSION 所有一维码格式
     */
    override fun configScanType(): ScanTypeConfig {
        return ScanTypeConfig.ONLY_QR_CODE
    }
}