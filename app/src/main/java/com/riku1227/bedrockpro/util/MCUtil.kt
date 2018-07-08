package com.riku1227.bedrockpro.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

class MCUtil(pm: PackageManager) {
    val packageName = "com.mojang.minecraftpe"
    private var packageInfo: PackageInfo? = null
    private var applicationInfo : ApplicationInfo? = null

    init {
        try{
            packageInfo = pm.getPackageInfo(packageName,0)
            applicationInfo = pm.getApplicationInfo(packageName,0)
        }catch(e : PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
    }

    fun getVersion() : String{
        var result = ""
        if(packageInfo != null) result = packageInfo!!.versionName
        return result
    }

    fun getVersionCode() : Long {
        var result: Long = 0
        if(packageInfo != null) {
            if(Build.VERSION.SDK_INT >= 28) {
                result = packageInfo!!.longVersionCode
            } else {
                result = packageInfo!!.versionCode.toLong()
            }
        }
        return result
    }

    fun getInstallLocation() : String {
        var result = ""
        if(packageInfo != null) result = applicationInfo!!.sourceDir
        return result
    }

    fun isInstalled() : Boolean {
        return packageInfo != null
    }
}