package jp.riku1227.bedrockpro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import jp.riku1227.bedrockpro.util.FileUtil

class BedrockPro {
    companion object {
        const val HOMEPATH = "BedrockPro/"
        const val BEDROCK_HOMEPATH = "games/com.mojang/"
        const val RESOURCEPACKS_PATH = "resource_packs/"

        fun getHomePath() : String {
            return FileUtil.getExternalStoragePath() + HOMEPATH
        }

        fun getCachePath() : String {
            return getHomePath() + "cache/"
        }

        fun getBedrockHomePath() : String {
            return FileUtil.getExternalStoragePath() + BEDROCK_HOMEPATH
        }

        fun getResourcePacksPath() : String {
            return getBedrockHomePath() + RESOURCEPACKS_PATH
        }
    }
}

fun webIntent(activity : Activity,urlString : String) {
    val uri = Uri.parse(urlString)
    val intent = Intent(Intent.ACTION_VIEW,uri)
    activity.startActivity(intent)
}

fun makeToast(context : Context,toastMessage : String) {
    Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT).show()
}

fun makeSnackBar(view: View,snackMessage : String) {
    Snackbar.make(view,snackMessage,Snackbar.LENGTH_SHORT).show()
}

fun makeThreadToast(handler : Handler,context: Context,toastMessage: String) {
    handler.post {
        makeToast(context,toastMessage)
    }
}