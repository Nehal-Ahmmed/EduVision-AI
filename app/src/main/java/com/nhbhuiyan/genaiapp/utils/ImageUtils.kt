// ImageUtils.kt
package com.nhbhuiyan.genaiapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

object ImageUtils {
    suspend fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
            } catch (e: Exception) {
                Log.e("ImageUtils", "Error converting URI to Bitmap: ${e.message}")
                null
            }
        }
    }

    // Optional: Resize bitmap to avoid large file sizes
    suspend fun resizeBitmap(bitmap: Bitmap, maxSize: Int = 1024): Bitmap {
        return withContext(Dispatchers.IO) {
            var width = bitmap.width
            var height = bitmap.height

            if (width > maxSize || height > maxSize) {
                val ratio = width.toFloat() / height.toFloat()
                if (ratio > 1) {
                    width = maxSize
                    height = (maxSize / ratio).toInt()
                } else {
                    height = maxSize
                    width = (maxSize * ratio).toInt()
                }

                Bitmap.createScaledBitmap(bitmap, width, height, true)
            } else {
                bitmap
            }
        }
    }
}