package jp.riku1227.mcbetool.fragment

import android.Manifest
import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_resource_pack_gen.*
import java.io.File
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

    private val deleteFileList = arrayOf("credits", "font", "materials", "texts", "blocks.json", "bug_pack_icon.png", "items_client.json", "items_offsets_client.json", "loading_messages.json", "manifest_publish.json", "splashes.json")

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
                        val resoluteDialogMessage = resources.getString(R.string.resource_pack_gen_dialog_name).format(resourcePackName) + "\n" +
                                resources.getString(R.string.resource_pack_gen_dialog_description).format(resourcePackDescription) + "\n" +
                                resources.getString(R.string.resource_pack_gen_dialog_header_uuid).format(resourcePackHeaderUUID) + "\n" +
                                resources.getString(R.string.resource_pack_gen_dialog_module_uuid).format(resourcePackModuleUUID)
                        val dialog = SimpleDialog.newInstance(resources.getString(R.string.resource_pack_gen_dialog_is_it_ok),resoluteDialogMessage)
                        dialog.setDialogListener(this)
                        dialog.show(fragmentManager,"SimpleDialog")
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
                resourcePackCustomIcon = true
            } else {
                resource_pack_gen_pick_custom_icon.visibility = View.GONE
                resourcePackCustomIcon = false
            }
        }

        resource_pack_gen_delete_cache.setOnClickListener {
            val file = File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/")
            val progress = ProgressDialog()
            progress.show(fragmentManager,"ProgressDialog")
            thread {
                file.deleteRecursively()
                progress.dismiss()
            }
        }
    }

    override fun onPositiveClick() {
        generateResourcePack()
    }

    override fun onNegativeClick() {
    }

    private fun generateResourcePack() {
        val mcbeUtil = MCBEUtil(activity.packageManager)
        val handler = Handler()
        val cacheFolder = "MCBETool/cache/"
        val resourceFolder = cacheFolder+"resource/"
        FileUtil.createFile(cacheFolder+".nomedia")
        val progress = ProgressDialog()
        val outDirectory = FileUtil.getExternalStoragePath() + "games/com.mojang/resource_packs/" + resourcePackName + "/"
        progress.show(fragmentManager,"ProgressDialog")
        thread {
            if(FileUtil.getFolderSize(FileUtil.getExternalStoragePath() + resourceFolder) <= 25000000) {
                File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/").deleteRecursively()
                makeThreadToast(handler,context,"APK unzip...")
                FileUtil.unzip(mcbeUtil.getinstallLocation()!!,resourceFolder,"assets/resource_packs/vanilla/")
                makeThreadToast(handler,context,"Move cache resource file")
                File(FileUtil.getExternalStoragePath()+resourceFolder+"assets/resource_packs/vanilla/").copyRecursively(File(FileUtil.getExternalStoragePath()+resourceFolder))
                FileUtil.deleteFile(FileUtil.getExternalStoragePath()+resourceFolder+"assets/")
            }
            makeThreadToast(handler,context,"Copy resource file to " + outDirectory)
            File(FileUtil.getExternalStoragePath()+resourceFolder).copyRecursively(File(outDirectory))
            makeThreadToast(handler,context,"Delete unnecessary file")
            for (i in 0 until deleteFileList.size) {
                FileUtil.deleteFile(outDirectory+deleteFileList[i])
            }
            makeThreadToast(handler,context,"Edit manifest.json")
            MCBEUtil.editManifest(outDirectory+"manifest.json",resourcePackName,resourcePackDescription,resourcePackHeaderUUID,resourcePackModuleUUID)
            if(!resourcePackCache) {
                makeThreadToast(handler,context,"Delete cache")
                File(FileUtil.getExternalStoragePath()+"MCBETool/cache/resource/").deleteRecursively()
            }
            progress.dismiss()
        }
    }
}