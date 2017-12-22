package jp.riku1227.bedrockpro.fragment

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.bedrockpro.R
import jp.riku1227.bedrockpro.webIntent
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : android.support.v4.app.Fragment() {

    private var pm : PackageManager? = null
    private var packageInfo : PackageInfo? = null
    private var res : Resources? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_home,container,false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "BedrockPro"

        pm = activity?.packageManager
        packageInfo = pm?.getPackageInfo(activity?.applicationContext?.packageName,0)
        res = activity?.baseContext?.resources

        appVersionTextView.text = res?.getString(R.string.app_version)?.format(packageInfo?.versionName)
        appVersionCodeTextView.text = res?.getString(R.string.app_version_code)?.format(packageInfo?.versionCode)

        aboutGithubButton.setOnClickListener { webIntent(activity!!, "https://github.com/riku1227/BedrockPro") }
        aboutGooglePlusButton.setOnClickListener { webIntent(activity!!, "https://plus.google.com/103470090583882439463") }
        aboutYoutubeButton.setOnClickListener { webIntent(activity!!, "https://www.youtube.com/channel/UCIhk2bb4Y8kPCnjCeiEVtmA") }
        aboutWebsiteButton.setOnClickListener { webIntent(activity!!, "https://riku1227.github.io") }
    }
}