package jp.riku1227.mcbetool.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

class UriUtil() {
    companion object {
        fun getBitmapFromUri(activity : Activity, uri : Uri) : Bitmap {
            val parcelFileDescriptor = activity.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor.fileDescriptor
            return BitmapFactory.decodeFileDescriptor(fileDescriptor)
        }
    }
}