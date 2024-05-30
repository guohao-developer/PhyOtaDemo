package com.phy.ota_demo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.phy.ota_demo.OtaApp
import com.phy.ota_demo.R
import com.phy.ota_demo.basic.PhyActivity
import com.phy.ota_demo.bean.ResultModel
import com.phy.ota_demo.bean.UpgradeFile
import com.phy.ota_demo.databinding.ActivityQrcodeResultBinding
import com.phy.ota_demo.utils.FileComparator
import com.phy.otalib.PhyCore
import com.phy.otalib.UpgradeCallback
import com.phy.otalib.bean.BasePhyDevice
import com.phy.otalib.scan.PhyScan
import com.phy.otalib.scan.PhyScanCallback
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.Collections
import kotlin.properties.Delegates


class QrcodeResultActivity : PhyActivity<ActivityQrcodeResultBinding>() , View.OnClickListener , PhyScanCallback ,
    UpgradeCallback {

    private lateinit var phyScan: PhyScan
    private lateinit var phyDevice: BluetoothDevice
    private lateinit var phyCore : PhyCore
    private val mHandler = MyHandler(WeakReference(this))

    // 选择升级文件结果回调
    private val openUpgradeFileList= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            // 升级文件已经获取
            val position = result.data?.getIntExtra("position",-1)
            if (position != null && position != -1){
                isUpgradeFileChecked = true
                filePath = fileList[position].file.absolutePath
            }
            showMsg("升级文件已经获取")
        }else{
            showMsg("升级文件没有获取")
        }
    }

    // 请求文件读取权限成功
    private val requestStorage =  registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { result: Boolean ->
        if (result) {
            // 加载升级文件
            loadFile()
        }
    }
    //请求定位权限意图
    private val requestLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //扫描蓝牙
                phyScan.startScan()
            } else {
                showMsg("Android12以下，6及以上需要定位权限才能扫描设备")
            }
        }

    //请求BLUETOOTH_CONNECT权限意图
    private val requestBluetoothConnect =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //打开蓝牙
                enableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }

    //请求BLUETOOTH_SCAN权限意图
    private val requestBluetoothScan =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //进行扫描
                phyScan.startScan()
            }
        }

    //打开蓝牙意图
    private val enableBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            if (isOpenBluetooth()) {
//                showMsg("蓝牙已打开")
            } else {
//                showMsg("蓝牙未打开")
            }
        }
    }

    // 文件路径 选择升级文件
    private val path: String = Environment.getExternalStorageDirectory().absolutePath
    private val appPath: String = OtaApp.context.getExternalFilesDir(null)!!.absolutePath
    private val fileList = ArrayList<UpgradeFile>()

    // 从网络上获取的UUID
    var imeiUuid : String = "等待获取UUID"
    // 设备对应MAC地址
    var macAddress : String = "等待获取MAC地址"
    // 已选中的升级文件路径
    lateinit var filePath : String
    // 二维码解析数据
    private lateinit var deviceCode : String

    // 二维码扫描 或者 文本输入
    private var type by Delegates.notNull<Int>()
    private var isUpgradeFileChecked : Boolean = false
    var isIMEIGeted : Boolean = false

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate() {
        // 获取从二维码中扫描到的数据
        deviceCode = intent.getStringExtra("data")!!
        type = intent.getIntExtra("type" , -1)


        // 设置按钮点击事件
        binding.btnScanDevice.setOnClickListener(this)
        binding.btnSelectUpgradefile.setOnClickListener(this)
        binding.btnLinkDevice.setOnClickListener(this)
        binding.btnSendUpgradefile.setOnClickListener(this)

        // 初始化升级文件过程回调
        phyCore = PhyCore.getInstance(this)
        phyCore.setUpgradeCallback(this)

        // 初始化蓝牙扫描器
        phyScan = PhyScan.getInstance(this)
        phyScan.setPhyScanCallback(this)

        // 从服务器获取设备的IMEI值
        onRequestUuid(deviceCode , this , type)

        // 申请蓝牙权限和文件读写权限
        onRegister()

        // 显示返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    // 权限获取
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRegister() {
        // Manager_External_Storage权限

//        val permissions = {Manifest.permission.READ_EXTERNAL_STORAGE}
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (isAndroid12 || isAndroid11){
            if (!Environment.isExternalStorageManager()){
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:$packageName"));
                startActivityForResult(intent, 1024);
            }
        }else{
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this , permissions , 2)
            }
        }


        //是Android12
        if (isAndroid12) {
            //检查是否有BLUETOOTH_SCAN权限
            if (hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                //打开蓝牙
                enableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            } else {
                //请求权限
                requestBluetoothConnect.launch(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }else{
            //不是Android12 直接打开蓝牙
            enableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }

    }

    // 显示隐藏的控件
    private fun makeViewsVisible() {
        binding.btnSelectUpgradefile.visibility = View.VISIBLE
        binding.relativeLayoutMsgShow.visibility = View.VISIBLE
        binding.MACText.visibility = View.VISIBLE
        binding.btnScanDevice.setBackgroundColor( Color.GRAY)
        binding.btnScanDevice.isEnabled = false
        binding.imeiText.text = imeiUuid
        binding.MACText.text = macAddress
        binding.btnSendUpgradefile.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            // 寻找设备
            R.id.btn_scan_device -> {
                showMsg("等待获取MAC地址")

                // 检查是否已经获得IMEI号
                if (!isIMEIGeted){
                    showMsg("等待获取设备IMEI号")
                    onRequestUuid(deviceCode ,this ,type)
                    return
                }

                if (isAndroid12) {
                    if (hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
                        //扫描或者停止扫描
                        if (!phyScan.isScanning) {
                            phyScan.startScan()
                        }
                    }else {
                        requestBluetoothScan.launch(Manifest.permission.BLUETOOTH_SCAN)
                    }
                } else {
                    if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        //开始扫描
                        if (!phyScan.isScanning) {
                            phyScan.startScan()
                        }
                    } else {
                        requestLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }

            // 连接设备
            R.id.btn_link_device -> {
                // 检查是否已经停止扫描
                if (phyScan.isScanning){
                    showMsg("正在扫描指定蓝牙设备 请稍等")
                    return
                }

                // 连接蓝牙设备
                if (phyDevice != null && !phyCore.isConnected) {
                    phyCore.connect(phyDevice)
                }
            }

            // 选择升级文件
            R.id.btn_select_upgradefile -> {
                // 读取文件
                loadFile()
                if (!isUpgradeFileChecked){
                    // 选择升级文件 获取文件名称
                    val intent = Intent(this ,UpgradeFileSelectActivity::class.java )
                    intent.putExtra("fileList" , fileList)
                    openUpgradeFileList.launch(intent)
                    return
                }else{
                    showMsg("已经选择升级文件")
                }
            }

            // 发送升级文件
            R.id.btn_send_upgradefile -> {
                // 检查是否选择升级文件
                if (!isUpgradeFileChecked){
                    showMsg("未选择升级文件 请稍等")
                    return
                }
                // 设备是否已经连接
                if (!phyCore.isConnected) {
                    showMsg("设备还未连接")
                    return
                }

                if (!phyCore.isReady){
                    showMsg("设备还未准备好")
                    return
                }

                // 是否在升级中
                if (phyCore.isUpgrade) {
                    showMsg("当前设备正在升级")
                    return
                }else{
                    // 发送升级文件
                    phyCore.startUpgrade(filePath)
                    showMsg("正在发送升级文件")
                }

            }
        }
    }

    // 重写蓝牙扫描结果PhyScanCallback回调方法
    @SuppressLint("MissingPermission")
    override fun onScanResult(result: ScanResult?) {
        val device = result?.device

        if (device?.name != null){
            Log.i("guohao" , device.name)
        }
        if (device?.name != null && device?.name == imeiUuid){
            phyDevice = device
            macAddress = device.address
            val msg = Message.obtain(mHandler)
            msg.what = 2 // 消息标识
            msg.obj = "isMacAddressGeted" // 消息内容存放
            mHandler.sendMessage(msg)
            phyScan.stopScan()
        }
    }

    // 批量回调
    override fun onBatchScanResults(p0: MutableList<ScanResult>?) { }

    // 扫描失败
    override fun onScanFailed(p0: String?) { }

    private fun onRequestUuid(deviceCode : String, context : Context , type : Int){

        when(type){
            1 -> {
                runOnThread()
            }
            2 -> {
                imeiUuid = deviceCode
                // 实例化消息对象
                val msg = Message.obtain(mHandler)
                msg.what = 1 // 消息标识
                msg.obj = "isIMEIGeted" // 消息内容存放
                mHandler.sendMessage(msg)
            }

        }

    }

    private fun runOnThread(){
        runOnUiThread {
            val client = OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("devicePrintCode", deviceCode)
                .build()
            val request = Request.Builder()
                .url("https://76m5f07853.zicp.fun/box/lnApp/device/getImeiAndBleVersionByDevicePrintCode?devicePrintCode=$deviceCode")
                .get()
                .build()


            val call: Call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    val resultModel = Gson().fromJson(responseBody, ResultModel::class.java)
                    when (resultModel.code) {
                        200 -> {
                            // 获取IMEI号
                            val items = resultModel.data.toString().split(",")
                            var i = 0
                            for (item in items) {
                                if (item.contains("imei")) {
                                    imeiUuid = item.split("=")[1]
                                }
                            }
                            // 实例化消息对象
                            val msg = Message.obtain(mHandler)
                            msg.what = 1 // 消息标识
                            msg.obj = "isIMEIGeted" // 消息内容存放
                            mHandler.sendMessage(msg)
                        }

                        400 -> {
                            val msg = Message.obtain(mHandler)
                            msg.what = 5 // 消息标识
                            msg.arg1 = 1 // 消息内容存放
                            mHandler.sendMessage(msg)
                        }

                        404 -> {
                            val msg = Message.obtain(mHandler)
                            msg.what = 5 // 消息标识
                            msg.arg1 = 2 // 消息内容存放
                            mHandler.sendMessage(msg)
                        }

                        500 -> {
                            val msg = Message.obtain(mHandler)
                            msg.what = 5 // 消息标识
                            msg.arg1 = 3 // 消息内容存放
                            mHandler.sendMessage(msg)
                        }
                    }

                }
            })
        }
    }

    // 加载升级文件列表
    private fun loadFile() {
        fileList.clear()
        val file = File(path)
        if (file.exists()) {
            val listFiles = file.listFiles()
            if (listFiles != null) {
                for (f in listFiles) {
                    if (f.name.endsWith(".hex16")
                        || f.name.endsWith(".hex")
                        || f.name.endsWith(".hexe")
                        || f.name.endsWith(".res")
                        || f.name.endsWith(".hexe16")
                        || f.name.endsWith(".bin")
                    ) {
                        fileList.add(UpgradeFile(f, false))
                        Log.i("guohao1" , f.name)
                    }

                    Log.i("guohao" , f.name)
                }
                //按文件创建时间排序
                Collections.sort(fileList, FileComparator())
            } else {
                showMsg("本地文件夹无法打开")
            }
        } else {
            showMsg("无法获取文件")
        }

        //从应用文件夹下获取文件
        val appFile = File(appPath)
        if (appFile.exists()) {
            val listFiles = appFile.listFiles()
            if (listFiles != null) {
                for (f in listFiles) {
                    if (f.name.endsWith(".hex16")
                        || f.name.endsWith(".hex")
                        || f.name.endsWith(".hexe")
                        || f.name.endsWith(".res")
                        || f.name.endsWith(".hexe16")
                        || f.name.endsWith(".bin")
                    ) {
                        fileList.add(UpgradeFile(f, false))
                    }

                    Log.i("guohao" , f.name)
                }
                //按文件创建时间排序
                Collections.sort(fileList, FileComparator())
            } else {
                showMsg("本地文件夹无法打开")
            }
        } else {
            showMsg("无法获取文件")
        }
    }

    // 升级文件回调
    override fun onProgress(p0: Double) {
        if (p0 > 96){
            val msg = Message.obtain(mHandler)
            msg.what = 3 // 消息标识
            msg.arg1 = p0.toInt() // 消息内容存放
            mHandler.sendMessage(msg)
        }
    }

    override fun onStatus(p0: String?) {

        showMsg("升级文件状态：$p0")
    }

    override fun onFailed(p0: BasePhyDevice?) {
        showMsg("升级文件失败")

    }

    override fun onSuccess() {
        val msg = Message.obtain(mHandler)
        msg.what = 4 // 消息标识
        mHandler.sendMessage(msg)
    }

    override fun onSpecialExceptionHandling(p0: Int) {

    }

    private class MyHandler(val wrActivity: WeakReference<QrcodeResultActivity>) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            wrActivity.get()?.run {
                when (msg.what) {
                    1 -> {
                        binding.showIMEICode.text = imeiUuid
                        isIMEIGeted = true
                    }
                    2 -> {
                        binding.MACText.text = macAddress
                        // 显示隐藏控件
                        makeViewsVisible()
                        showMsg("已获取MacAddress")
                    }
                    3 -> {
                        binding.numberProgressBar.progress = msg.arg1
//                        showMsg("升级已完成")
                    }
                    4 -> {
                        phyCore.disconnect()
                        Toast.makeText(context, "升级成功", Toast.LENGTH_SHORT).show()
                    }
                    5 -> {
                        if (msg.arg1 == 1){
                            Toast.makeText(context, "请求参数错误", Toast.LENGTH_SHORT).show()
                        }else if (msg.arg1 == 2){
                            Toast.makeText(context, "锁未找到", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "服务器错误 未能获取到IMEI号", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2 && grantResults.isNotEmpty()){

            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_DENIED) {
                // 权限被拒绝
                Log.i("guohao" ,"低版本Android 申请失败")
            }else{
                // 申请成功
                Log.i("guohao" ,"低版本Android 申请成功")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1024 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 检查是否有权限
            if (Environment.isExternalStorageManager()) {
                showMsg("获取文件所有权限")
            } else {
                showMsg("无法获取文件所有权限")
            }
        }
    }
}