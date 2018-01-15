package jp.riku1227.bedrockpro.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import java.io.*

class BedrockUtil(pm : PackageManager) {

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
        fun editManifest(filePath : String, packName : String, packDescription : String, packHeaderUUID : String, packModuleUUID : String,subPackCardNameList : ArrayList<String>? = null, subPackCardDirectoryList : ArrayList<String>? = null, subPackCardMemoryTierList : ArrayList<String>? = null) : String {
            val manifestFile = File(filePath)
            if(!manifestFile.exists()) {
                manifestFile.createNewFile()
            }
            var fileContents = ""
            var mode = 0
            var subpack = false
            manifestFile.forEachLine {
                var str : String
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
                    it.indexOf("\"version\"") != -1 -> {
                        if(mode == 0) {
                            str = it
                        } else {
                            str = it
                            subpack = true
                        }
                    }
                    it.indexOf("]") != -1 -> {
                        if(subpack) {
                            if(subPackCardNameList != null) {
                                str = "    ],\n    \"subpacks\": [\n"
                                var num = 0

                                for(i in 0 until subPackCardNameList.size) {
                                    val folderName = subPackCardDirectoryList!![i]
                                    val name = subPackCardNameList!![i]
                                    val memoryTier = subPackCardMemoryTierList!![i]

                                    if(subPackCardNameList.size - 1 == i) {
                                        str += "        {\n"+
                                                "            \"folder_name\": \"$folderName\",\n"+
                                                "            \"name\": \"$name,\"\n"+
                                                "            \"memory_tier\": \"$memoryTier\"\n"+
                                                "        }\n"+
                                                "    ]"
                                    } else {
                                        str += "        {\n"+
                                                "            \"folder_name\": \"$folderName\",\n"+
                                                "            \"name\": \"$name,\"\n"+
                                                "            \"memory_tier\": \"$memoryTier\"\n"+
                                                "        },\n"
                                    }
                                }
                            } else {
                                str = it
                            }
                        } else {
                            str = it
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