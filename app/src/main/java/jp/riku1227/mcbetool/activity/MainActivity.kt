package jp.riku1227.mcbetool.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.widget.Button
import android.widget.TextView
import jp.riku1227.mcbetool.R
import jp.riku1227.mcbetool.webIntent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var pm : PackageManager? = null
    private var packageInfo : PackageInfo? = null
    private var res : Resources? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name,
                R.string.app_version_code)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        pm = packageManager
        packageInfo = pm?.getPackageInfo(applicationContext.packageName,0)
        res = baseContext.resources

        app_version_text_view.text = res?.getString(R.string.app_version)?.format(packageInfo?.versionName)
        app_version_code_text_view.text = res?.getString(R.string.app_version_code)?.format(packageInfo?.versionCode)

        about_github_button.setOnClickListener { webIntent(this, "https://github.com/riku1227/MCBETool") }
        about_google_plus_button.setOnClickListener { webIntent(this, "https://plus.google.com/103470090583882439463") }
        about_youtube_button.setOnClickListener { webIntent(this, "https://www.youtube.com/channel/UCIhk2bb4Y8kPCnjCeiEVtmA") }
        about_homepage_button.setOnClickListener { webIntent(this, "https://riku1227.github.io") }
    }
}
