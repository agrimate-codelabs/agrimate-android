package com.codelabs.agrimate.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object CameraUtils {
    fun takePicture(
        context: Context,
        imageCapture: ImageCapture,
        onSuccessCallback: (uri: Uri?) -> Unit = {},
        onFailureCallback: (message: String?) -> Unit = {},
    ) {
        val timeStamp = System.currentTimeMillis()
        val imageFileName = "agrimate-check-disease-$timeStamp.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/Agrimate"
                )
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onSuccessCallback(outputFileResults.savedUri)
                }

                override fun onError(exception: ImageCaptureException) {
                    onFailureCallback(exception.message.toString())
                }

            }
        )
    }

    fun imageUriToFile(uri: Uri, context: Context): File? {
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri) as InputStream

            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)

            val orientation = getOrientation(context, uri)
            val rotatedBitmap = rotateBitmap(bitmap!!, orientation)

            val temp = createTempFile(context)

            var compressQuality = 90
            var outputStream: OutputStream

            val maxFileSize = 2 * 1024 * 1024

            do {
                outputStream = FileOutputStream(temp)
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, outputStream)
                outputStream.close()

                compressQuality -= 10
            } while (temp.length() > maxFileSize && compressQuality > 0)

            inputStream.close()
            Log.d("TEST", "Test compress result: ${temp.length()}")
            return temp
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun getOrientation(context: Context, uri: Uri): Int {
        val contentResolver: ContentResolver = context.contentResolver
        val exif = ExifInterface(contentResolver.openInputStream(uri)!!)
        return exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(270f)
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun createTempFile(context: Context): File {
        val timestamp = System.currentTimeMillis()
        val imageFileName = "Agrimate_IMG$timestamp"

        return File.createTempFile(
            imageFileName,
            ".jpg",
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
    }
}
