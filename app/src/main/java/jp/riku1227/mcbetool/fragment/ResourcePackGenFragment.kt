package jp.riku1227.mcbetool.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.content.PermissionChecker
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.mcbetool.R
import jp.riku1227.mcbetool.dialog.DialogListener
import jp.riku1227.mcbetool.dialog.PermissionDialog
import jp.riku1227.mcbetool.dialog.ProgressDialog
import jp.riku1227.mcbetool.dialog.SimpleDialog
import jp.riku1227.mcbetool.makeSnackBar
import jp.riku1227.mcbetool.makeThreadToast
import jp.riku1227.mcbetool.makeToast
import jp.riku1227.mcbetool.util.FileUtil
import jp.riku1227.mcbetool.util.MCBEUtil
import jp.riku1227.mcbetool.util.UriUtil
import kotlinx.android.synthetic.main.fragment_resource_pack_gen.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.concurrent.thread

class ResourcePackGenFragment : android.support.v4.app.Fragment() , DialogListener {

    private var resourcePackName = ""
    private var resourcePackDescription = ""
    private var resourcePackHeaderUUID = ""
    private var resourcePackModuleUUID = ""

    private var resourcePackCache = true
    private var resourcePackAutoGenUUID = true
    private var resourcePackCustomIcon = false

    private var resourcePackCustomIconBitmap : Bitmap? = null

    private val deleteFileList = arrayOf("credits", "font", "materials", "texts", "blocks.json", "bug_pack_icon.png", "items_client.json", "items_offsets_client.json", "loading_messages.json", "manifest_publish.json", "splashes.json")


    private var resoluteDialogMessage = ""
    private var resourcePackGenResoluteCheckDialog : SimpleDialog? = null

    private var RESULT_PICK_IMAGEFILE = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_resource_pack_gen,container,false)
    }

    override fun onStart() {
        super.onStart()

        resourcePackCache = resource_pack_gen_resource_cache.isChecked
        resourcePackAutoGenUUID = resource_pack_gen_auto_gen_uuid.isChecked
        resourcePackCustomIcon = resource_pack_gen_custom_pack_icon.isChecked

        if(PermissionChecker.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            PermissionDialog().show(fragmentManager,"PermissionDialog")
        }

        resource_pack_gen_generate.setOnClickListener {
            if(PermissionChecker.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
                makeToast(context,resources.getString(R.string.permission_is_not_granted))
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", context.packageName, null)
                startActivity(intent)
            } else {
                if(!MCBEUtil(activity.packageManager).isInstalled()) {
                    makeSnackBar(view!!,resources.getString(R.string.mcpe_is_not_installed))
                } else {
                    if(resource_pack_gen_name.text.toString() == "") {
                        makeSnackBar(view!!,resources.getString(R.string.resource_pack_gen_not_input_name))
                    } else {
                        resourcePackName = resource_pack_gen_name.text.toString()
                        resourcePackDescription = resource_pack_gen_description.text.toString()
                        if(resourcePackAutoGenUUID) {
                            resourcePackHeaderUUID = UUID.randomUUID().toString()
                            resourcePackModuleUUID = UUID.randomUUID().toString()
                        } else {
                            resourcePackHeaderUUID = resource_pack_gen_header_uuid.text.toString()
                            resourcePackModuleUUID = resource_pack_gen_module_uuid.text.toString()
                        }

                        if(File(FileUtil.getExternalStoragePath() + "games/com.mojang/resource_packs/" + resourcePackName + "/").exists()) {
                            makeSnackBar(view!!,resources.getString(R.string.resource_pack_is_exists))
                        } else {
                            resoluteDialogMessage = resources.getString(R.string.resource_pack_gen_dialog_name).format(resourcePackName) + "\n" +
                                    resources.getString(R.string.resource_pack_gen_dialog_description).format(resourcePackDescription) + "\n" +
                                    resources.getString(R.string.resource_pack_gen_dialog_header_uuid).format(resourcePackHeaderUUID) + "\n" +
                                    resources.getString(R.string.resource_pack_gen_dialog_module_uuid).format(resourcePackModuleUUID)
                            resourcePackGenResoluteCheckDialog = SimpleDialog.newInstance(resources.getString(R.string.resource_pack_gen_dialog_is_it_ok),resoluteDialogMessage)
                            resourcePackGenResoluteCheckDialog!!.setDialogListener(this)
                            val versionTxtFile = File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/version.txt")
                            if (versionTxtFile.exists()) {
                                if(versionTxtFile.readText() != MCBEUtil(activity.packageManager).getVersion()) {
                                    val versionErrorDialog = SimpleDialog.newInstance("バージョンエラー",
                                            "キャッシュのバージョンとMinecraft PEのバージョンが一致していません\nキャッシュを削除しますか？",
                                            "はい","このまま生成する")
                                    versionErrorDialog.setDialogListener(this)
                                    versionErrorDialog.show(fragmentManager,"resource_pack_gen_cache_version_error")
                                } else {
                                    resourcePackGenResoluteCheckDialog!!.show(fragmentManager,"resource_pack_gen_resolute_check_dialog")
                                }
                            } else {
                                resourcePackGenResoluteCheckDialog!!.show(fragmentManager,"resource_pack_gen_resolute_check_dialog")
                            }
                        }
                    }
                }
            }
        }

        resource_pack_gen_resource_cache.setOnClickListener {
            resourcePackCache = resource_pack_gen_resource_cache.isChecked
        }

        resource_pack_gen_auto_gen_uuid.setOnClickListener {
            if(resource_pack_gen_auto_gen_uuid.isChecked){
                resource_pack_gen_header_uuid.text = SpannableStringBuilder("")
                resource_pack_gen_module_uuid.text = SpannableStringBuilder("")
                resource_pack_gen_header_uuid.visibility = View.GONE
                resource_pack_gen_module_uuid.visibility = View.GONE
                resourcePackAutoGenUUID = true
            } else {
                resource_pack_gen_header_uuid.visibility = View.VISIBLE
                resource_pack_gen_module_uuid.visibility = View.VISIBLE
                resourcePackAutoGenUUID = false
            }
        }

        resource_pack_gen_custom_pack_icon.setOnClickListener {
            if(resource_pack_gen_custom_pack_icon.isChecked) {
                resource_pack_gen_pick_custom_icon.visibility = View.VISIBLE
                resource_pack_gen_custom_pack_icon_card.visibility = View.VISIBLE
                resourcePackCustomIcon = true
            } else {
                resource_pack_gen_pick_custom_icon.visibility = View.GONE
                resource_pack_gen_custom_pack_icon_card.visibility = View.GONE
                resourcePackCustomIcon = false
            }
        }

        resource_pack_gen_pick_custom_icon.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent,RESULT_PICK_IMAGEFILE)
        }

        resource_pack_gen_delete_cache.setOnClickListener {
            deleteCache()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            val uri : Uri
            if (data != null) {
                uri = data.data
                resourcePackCustomIconBitmap = UriUtil.getBitmapFromUri(activity,uri)
                resource_pack_gen_custom_pack_icon_iv.setImageBitmap(resourcePackCustomIconBitmap)
            }
        }
    }

    override fun onPositiveClick(tag : String) {
        when (tag) {
            "resource_pack_gen_resolute_check_dialog" -> generateResourcePack()
            "resource_pack_gen_cache_version_error" -> deleteCache(true)
        }
    }

    override fun onNegativeClick(tag : String) {
        when (tag) {
            "resource_pack_gen_cache_version_error" -> resourcePackGenResoluteCheckDialog!!.show(fragmentManager,"resource_pack_gen_resolute_check_dialog")
        }
    }

    private fun deleteCache(dialogShow : Boolean = false) {
        val file = File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/")
        val progress = ProgressDialog()
        progress.show(fragmentManager,"ProgressDialog")
        thread {
            file.deleteRecursively()
            progress.dismiss()
            if(dialogShow) {
                resourcePackGenResoluteCheckDialog!!.show(fragmentManager,"resource_pack_gen_resolute_check_dialog")
            }
        }
    }

    private fun generateResourcePack() {
        val mcbeUtil = MCBEUtil(activity.packageManager)
        val handler = Handler()
        val cacheFolder = FileUtil.getExternalStoragePath() + "MCBETool/cache/"
        val resourceFolder = cacheFolder+"resource/"
        val assetsFolder = resourceFolder + "assets/resource_packs/vanilla/"
        val outFolder = FileUtil.getExternalStoragePath() + "games/com.mojang/resource_packs/" + resourcePackName + "/"
        FileUtil.createFile(cacheFolder+".nomedia")
        val progress = ProgressDialog()
        progress.show(fragmentManager,"generate_resource_pack_progress_dialog")
        thread {
            if(FileUtil.getFolderSize(assetsFolder) <= 25000000) {
                File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/").deleteRecursively()
                makeThreadToast(handler,context,"APK unzip...")
                FileUtil.unzip(mcbeUtil.getinstallLocation()!!,resourceFolder,"assets/resource_packs/vanilla/")
                FileUtil.createTxtFile(resourceFolder+"version.txt",mcbeUtil.getVersion()!!)
            }
            makeThreadToast(handler,context,"Copy resource file to " + outFolder)
            File(assetsFolder).copyRecursively(File(outFolder))
            makeThreadToast(handler,context,"Delete unnecessary file")
            for (i in 0 until deleteFileList.size) {
                FileUtil.deleteFile(outFolder + deleteFileList[i])
            }
            makeThreadToast(handler,context,"Edit manifest.json")
            MCBEUtil.editManifest(outFolder + "manifest.json",resourcePackName,resourcePackDescription,resourcePackHeaderUUID,resourcePackModuleUUID)
            if(resourcePackCustomIcon) {
                File(outFolder+"pack_icon.png").delete()
                val fileOutputStream = FileOutputStream(File(outFolder+"pack_icon.png"))
                resourcePackCustomIconBitmap?.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream)
            }
            if(!resourcePackCache) {
                makeThreadToast(handler,context,"Delete cache")
                File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/").deleteRecursively()
            }
            progress.dismiss()
        }
    }
}