package jp.riku1227.bedrockpro.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class UriUtil {
    companion object {
        fun copyFileFromUri(context : Context, uri : Uri, toPath : String) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(File(toPath))
            val bufferedInput = BufferedInputStream(inputStream)
            val bufferedOutput = BufferedOutputStream(outputStream)

            var i = 0
            val b = ByteArray(8192)
            while ({i = bufferedInput.read(b); i}() != -1) {
                bufferedOutput.write(b,0,i)
            }
            bufferedOutput.flush()
            bufferedInput.close()
            bufferedOutput.close()
        }

        fun getBitmapFromUri(activity : Activity, uri : Uri) : Bitmap {
            val parcelFileDescriptor = activity.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor.fileDescriptor
            return BitmapFactory.decodeFileDescriptor(fileDescriptor)
        }
    }
}