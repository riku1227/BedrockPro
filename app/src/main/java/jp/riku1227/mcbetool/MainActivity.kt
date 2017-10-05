package jp.riku1227.mcbetool

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    }
}