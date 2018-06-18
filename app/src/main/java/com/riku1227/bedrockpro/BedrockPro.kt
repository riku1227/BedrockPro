package com.riku1227.bedrockpro

import android.app.Activity
import android.content.Intent
import android.net.Uri

class BedrockPro {
    companion object {
        const val packageName = "com.riku1227.bedrockpro"

        fun webIntent(activity : Activity, urlString : String) {
            val uri = Uri.parse(urlString)
            val intent = Intent(Intent.ACTION_VIEW,uri)
            activity.startActivity(intent)
        }
    }
}