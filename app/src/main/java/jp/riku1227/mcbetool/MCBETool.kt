package jp.riku1227.mcbetool

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun webIntent(activity : Activity,urlString : String) {
    val uri = Uri.parse(urlString)
    val intent = Intent(Intent.ACTION_VIEW,uri)
    activity.startActivity(intent)
}