package com.codelabs.agrimate.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream

interface FreezeCallback {
    fun onLastFrameCaptured(bitmap: Bitmap)
}

class FreezeAnalyzer(private val callback: FreezeCallback) : ImageAnalysis.Analyzer {

    private var flag = false

    override fun analyze(image: ImageProxy) {
        if (flag) {
            flag = false
            val bitmap = toBit(image)
            callback.onLastFrameCaptured(bitmap)
        }
        image.close()
    }

    fun freeze() {
        flag = true
    }

    private fun toBit(image: ImageProxy): Bitmap {
        // Convert the imageProxy to Bitmap
        val planes = image.planes
        val yBuffer = planes[0].buffer // Y
        val vuBuffer = planes[2].buffer // VU

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()

        var bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        // Rotate the bitmap
        val rotationDegrees = image.imageInfo.rotationDegrees.toFloat()
        if (rotationDegrees != 0f) {
            val matrix = Matrix()
            matrix.postRotate(rotationDegrees)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
        return bitmap
    }
}