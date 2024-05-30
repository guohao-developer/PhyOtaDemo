package com.phy.ota_demo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.provider.SyncStateContract
import android.text.TextUtils
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import kotlin.math.min

class QRCodeUtil (val context: Context , private val resultHandler: (String?) -> Unit) : ImageAnalysis.Analyzer{


    private var mYBuffer = ByteArray(0)

    private val reader: MultiFormatReader = MultiFormatReader().apply {
        val map = mapOf<DecodeHintType, Collection<BarcodeFormat>>(
            Pair(DecodeHintType.POSSIBLE_FORMATS, arrayListOf(BarcodeFormat.QR_CODE))
        )
        setHints(map)
    }

    override fun analyze(image: ImageProxy) {

    }

    private fun proxyToBitmap(image: ImageProxy): Bitmap {
        Log.d("guohao", "proxyToBitmap start")
        val planes: Array<ImageProxy.PlaneProxy> = image.planes
        val yBuffer: ByteBuffer = planes[0].buffer
        val uBuffer: ByteBuffer = planes[1].buffer
        val vBuffer: ByteBuffer = planes[2].buffer

        val ySize: Int = yBuffer.remaining()
        val uSize: Int = uBuffer.remaining()
        val vSize: Int = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)
        //U and V are swapped
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage =
            YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)
        Log.d("guohao", "proxyToBitmap {width:${yuvImage.width} height:${yuvImage.height}}")

        val imageBytes = out.toByteArray()
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888

        var bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, opt)
        Log.d("guohao", "proxyToBitmap bitmap {width:${bitmap.width} height:${bitmap.height}}")

//        // Rotate bitmap to 90 degree.
//        val matrix = Matrix()
//        matrix.setRotate(
//            90f,
//            (bitmap.width / 2).toFloat(),
//            (bitmap.height / 2).toFloat()
//        )
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
//        Log.d(Constants.TAG_CAMERA, "proxyToBitmap rotated {width:${bitmap.width} height:${bitmap.height}}")

        Log.d("guohao", "proxyToBitmap end")
        return bitmap
    }


    private fun proxyToYuvImage(image: ImageProxy) : YuvImage{
        val planes: Array<ImageProxy.PlaneProxy> = image.planes
        val yBuffer: ByteBuffer = planes[0].buffer
        val uBuffer: ByteBuffer = planes[1].buffer
        val vBuffer: ByteBuffer = planes[2].buffer

        val ySize: Int = yBuffer.remaining()
        val uSize: Int = uBuffer.remaining()
        val vSize: Int = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        return yuvImage
    }

    private fun ImageProxy.toYBuffer(): ByteArray {
        val yPlane = planes[0]
        val yBuffer = yPlane.buffer
        yBuffer.rewind()
        val ySize = yBuffer.remaining()
        var position = 0
        if (mYBuffer.size != ySize) {
            Log.w("BarcodeAnalyzer", "swap buffer since size ${mYBuffer.size} != $ySize")
            mYBuffer = ByteArray(ySize)
        }
        // Add the full y buffer to the array. If rowStride > 1, some padding may be skipped.
        for (row in 0 until height) {
            yBuffer.get(mYBuffer, position, width)
            position += width
            yBuffer.position(min(ySize, yBuffer.position() - width + yPlane.rowStride))
        }
        return mYBuffer
    }
}