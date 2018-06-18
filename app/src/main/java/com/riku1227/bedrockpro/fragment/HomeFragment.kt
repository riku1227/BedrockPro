package com.riku1227.bedrockpro.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riku1227.bedrockpro.BedrockPro
import com.riku1227.bedrockpro.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onStart() {
        super.onStart()

        val packageInfo = context?.packageManager?.getPackageInfo(BedrockPro.packageName, 0)
        appVersion.text = resources.getString(R.string.app_version).format(packageInfo?.versionName)
        if(Build.VERSION.SDK_INT >= 28) {
            appVersionCode.text = resources.getString(R.string.app_version_code).format(packageInfo?.longVersionCode.toString())
        } else {
            appVersionCode.text = resources.getString(R.string.app_version_code).format(packageInfo?.versionCode.toString())
        }

        aboutWebsiteButton.setOnClickListener { BedrockPro.webIntent(activity!!, "https://riku1227.github.io/") }
        aboutGithubButton.setOnClickListener { BedrockPro.webIntent(activity!!, "https://github.com/riku1227/") }
        aboutGooglePlusButton.setOnClickListener { BedrockPro.webIntent(activity!!, "https://plus.google.com/103470090583882439463") }
    }
}