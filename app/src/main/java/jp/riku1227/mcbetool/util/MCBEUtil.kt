package jp.riku1227.mcbetool.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import java.io.*

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

    companion object {
        fun editManifest(filePath : String, packName : String, packDescription : String, packHeaderUUID : String, packModuleUUID : String) : String {
            val manifestFile = File(filePath)
            if(!manifestFile.exists()) {
                manifestFile.createNewFile()
            }
            var fileContents = ""
            var mode = 0
            manifestFile.forEachLine {
                val str : String
                when {
                    it.indexOf("\"header\"") != -1 -> {
                        mode = 0
                        str = it
                    }
                    it.indexOf("\"modules\"") != -1 -> {
                        mode = 1
                        str = it
                    }
                    it.indexOf("\"description\"") != -1 -> {
                        if(mode == 0) {
                            str = "        \"description\": \"$packDescription\","
                        } else {
                            str = "            \"description\": \"$packDescription\","
                        }
                    }
                    it.indexOf("\"name\"") != -1 -> str = "        \"name\": \"$packName\","
                    it.indexOf("\"uuid\"") != -1 -> {
                        if(mode == 0) {
                            str = "        \"uuid\": \"$packHeaderUUID\","
                        } else {
                            str = "            \"uuid\": \"$packModuleUUID\","
                        }
                    }
                    else -> str = it
                }
                fileContents +=str + "\n"
            }

            manifestFile.writeText(fileContents)
            return fileContents
        }
    }

}