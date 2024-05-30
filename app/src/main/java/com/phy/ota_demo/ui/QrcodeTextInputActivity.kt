package com.phy.ota_demo.ui

import android.content.Intent
import android.view.View
import com.phy.ota_demo.basic.PhyActivity
import com.phy.ota_demo.R
import com.phy.ota_demo.databinding.ActivityQrcodeStarterBinding
import com.phy.ota_demo.databinding.ActivityQrcodeTextBinding

class QrcodeTextInputActivity: PhyActivity<ActivityQrcodeTextBinding>() ,   View.OnClickListener {
    override fun onCreate() {

        binding.btnStartQrcodeByText.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_start_qrcode_byText -> {
                val result = binding.textInputEditText.text.toString()
                if (result.isNullOrEmpty()){
                    showMsg("未输入指定Imei号")
                    return
                }else{
                    val intent = Intent(context , QrcodeResultActivity::class.java)
                    intent.putExtra("data" , result)
                    intent.putExtra("type" , 2)
                    startActivity(intent)
                }

            }
        }
    }

}