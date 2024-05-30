package com.phy.ota_demo.ui

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.phy.ota_demo.basic.PhyActivity
import com.phy.ota_demo.databinding.ActivityQrcodeScanBinding
import com.phy.ota_demo.utils.QRCodeUtil
import com.phy.ota_demo.view.CusZxingView


class QrcodeScanerActivity : PhyActivity<ActivityQrcodeScanBinding>() {

    override fun onCreate() {
        // 动态申请相机权限
        if (hasPermission("Manifest.permission.CAMERA")) {
            binding.zxingview.synchLifeStart(this)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    // 权限申请结果回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                // 检查用户是否授予权限
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 执行权限操作
                    binding.zxingview.synchLifeStart(this)
                } else {
                    Toast.makeText(this, "相机权限获取失败", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}