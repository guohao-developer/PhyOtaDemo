package com.phy.ota_demo.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class UpgradeFile(var file : File, var check : Boolean) : Parcelable {
    fun isCheck(): Boolean {
        return check
    }
}
