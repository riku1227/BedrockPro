package jp.riku1227.mcbetool

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var pm : PackageManager? = null
    private var packageInfo : PackageInfo? = null
    private var res : Resources? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pm = packageManager
        packageInfo = pm?.getPackageInfo(applicationContext.packageName,0)
        res = baseContext.resources

        val appVersionTextView = findViewById<TextView>(R.id.app_version_text_view)
        val appVersionCodeTextView = findViewById<TextView>(R.id.app_version_code_text_view)

        appVersionTextView.text = res?.getString(R.string.app_version)?.format(packageInfo?.versionName)
        appVersionCodeTextView.text = res?.getString(R.string.app_version_code)?.format(packageInfo?.versionCode)

        val aboutGithubButton = findViewById<Button>(R.id.about_github_button)
        aboutGithubButton.setOnClickListener { webIntent(this,"https://github.com/riku1227/MCBETool") }
        val aboutGooglePlusButton = findViewById<Button>(R.id.about_google_plus_button)
        aboutGooglePlusButton.setOnClickListener { webIntent(this,"https://plus.google.com/103470090583882439463") }
        val aboutYoutubeButton = findViewById<Button>(R.id.about_youtube_button)
        aboutYoutubeButton.setOnClickListener { webIntent(this,"https://www.youtube.com/channel/UCIhk2bb4Y8kPCnjCeiEVtmA") }
        val aboutHomePageButton = findViewById<Button>(R.id.about_homepage_button)
        aboutHomePageButton.setOnClickListener { webIntent(this,"https://riku1227.github.io") }
    }
}