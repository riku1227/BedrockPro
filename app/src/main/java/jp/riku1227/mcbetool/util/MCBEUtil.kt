package jp.riku1227.mcbetool.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class MCBEUtil(pm : PackageManager) {

    private val packageName = "com.mojang.minecraftpe"
    private var packageInfo : PackageInfo? = null
    private var applicationInfo : ApplicationInfo? = null

    init {
        try{
            packageInfo = pm.getPackageInfo(packageName,0)
            applicationInfo = pm.getApplicationInfo(packageName,0)
        }catch(e : PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
    }

    fun getVersion() : String?{
        return packageInfo?.versionName
    }

    fun getVersionCode() : Int? {
        return packageInfo?.versionCode
    }

    fun getinstallLocation() : String? {
        return applicationInfo?.sourceDir
    }

    fun isInstalled() : Boolean {
        return packageInfo != null
    }

}