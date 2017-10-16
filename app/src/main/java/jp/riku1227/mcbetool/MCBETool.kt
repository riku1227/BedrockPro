package jp.riku1227.mcbetool

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

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