package jp.riku1227.mcbetool.fragment

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.mcbetool.R
import jp.riku1227.mcbetool.webIntent
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : android.support.v4.app.Fragment() {

    private var pm : PackageManager? = null
    private var packageInfo : PackageInfo? = null
    private var res : Resources? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_home,container,false)
    }

    override fun onStart() {
        super.onStart()
        activity.title = "MCBETools"

        pm = activity.packageManager
        packageInfo = pm?.getPackageInfo(activity.applicationContext.packageName,0)
        res = activity.baseContext.resources

        app_version_text_view.text = res?.getString(R.string.app_version)?.format(packageInfo?.versionName)
        app_version_code_text_view.text = res?.getString(R.string.app_version_code)?.format(packageInfo?.versionCode)

        about_github_button.setOnClickListener { webIntent(activity, "https://github.com/riku1227/MCBETool") }
        about_google_plus_button.setOnClickListener { webIntent(activity, "https://plus.google.com/103470090583882439463") }
        about_youtube_button.setOnClickListener { webIntent(activity, "https://www.youtube.com/channel/UCIhk2bb4Y8kPCnjCeiEVtmA") }
        about_website_button.setOnClickListener { webIntent(activity, "https://riku1227.github.io") }
    }
}