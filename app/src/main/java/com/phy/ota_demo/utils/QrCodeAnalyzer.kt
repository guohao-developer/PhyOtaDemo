package com.phy.ota_demo.utils

import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.client.android.DecodeFormatManager
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer
import java.util.Hashtable
import java.util.Vector

class QrCodeAnalyzer(private val resultHandler: (String?) -> Unit) : ImageAnalysis.Analyzer {
    private val reader: MultiFormatReader = initReader()

    /**
     * 将buffer写入数组
     */
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(image: ImageProxy) {//图片分析

        //如果不是yuv_420_888格式直接不处理
        if (ImageFormat.YUV_420_888 != image.format) {
            Log.e("BarcodeAnalyzer", "expect YUV_420_888, now = ${image.format}")
            image.close()
            return
        }

        //将buffer数据写入数组
        val data = image.planes[0].buffer.toByteArray()

        //获取图片宽高
        val height = image.height
        val width = image.width

        //将图片旋转，这是竖屏扫描的关键一步，因为默认输出图像是横的，我们需要将其旋转90度
        val rotationData = ByteArray(data.size)
        Log.i("TAG", "rotationDataSize :${data.size}  ## height:$height ## width:$width")
        var j: Int
        var k: Int
        for (y in 0 until height) {
            for (x in 0 until width) {
                j = x * height + height - y - 1
                k = x + y * width
                rotationData[j] = data[k]
            }
        }
        //zxing核心解码块，因为图片旋转了90度，所以宽高互换，最后一个参数是左右翻转
        val source = PlanarYUVLuminanceSource(rotationData, height, width, 0, 0, height, width, false)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            val result = reader.decode(bitmap)
            Log.i("Resultkkk", ":扫码成功： ${result.text}")
            resultHandler.invoke(result.text)

        } catch (e: Exception) {
            image.close()
        } finally {
            image.close()
        }
    }

    private fun initReader(): MultiFormatReader {
        val formatReader = MultiFormatReader()
        val hints = Hashtable<DecodeHintType, Any>()
        val decodeFormats = Vector<BarcodeFormat>()


        //添加二维码解码格式
//        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS)

        hints[DecodeHintType.POSSIBLE_FORMATS] = decodeFormats
        //设置解码的字符类型
        hints[DecodeHintType.CHARACTER_SET] = "UTF-8"
        //焦点回调，找到条码的所在位置
        formatReader.setHints(hints)
        return formatReader
    }
}