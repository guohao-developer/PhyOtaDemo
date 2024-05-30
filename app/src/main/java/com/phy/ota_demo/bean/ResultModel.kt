package com.phy.ota_demo.bean

data class ResultModel<T>(val success : String , val code : Int ,val message : String, val data: T , val tranceId : String)
