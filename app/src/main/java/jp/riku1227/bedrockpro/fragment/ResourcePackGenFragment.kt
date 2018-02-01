package jp.riku1227.bedrockpro.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.PermissionChecker
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.bedrockpro.R
import jp.riku1227.bedrockpro.`object`.SubpackData
import jp.riku1227.bedrockpro.dialog.DialogListener
import jp.riku1227.bedrockpro.dialog.PermissionDialog
import jp.riku1227.bedrockpro.dialog.ProgressDialog
import jp.riku1227.bedrockpro.dialog.SimpleDialog
import jp.riku1227.bedrockpro.makeSnackBar
import jp.riku1227.bedrockpro.makeToast
import jp.riku1227.bedrockpro.util.FileUtil
import jp.riku1227.bedrockpro.util.BedrockUtil
import jp.riku1227.bedrockpro.util.UriUtil
import kotlinx.android.synthetic.main.fragment_resource_pack_gen.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.concurrent.thread
import jp.riku1227.bedrockpro.activity.SubpackEditor

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

    private var subPackData : SubpackData? = SubpackData(arrayListOf(""),arrayListOf(""),arrayListOf(""))


    private var resoluteDialogMessage = ""
    private var resourcePackGenResoluteCheckDialog : SimpleDialog? = null

    private var resultPickImageFile = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_resource_pack_gen,container,false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "ResourcePackGen"

        resourcePackCache = resourcePackGenResourceCache.isChecked
        resourcePackAutoGenUUID = resourcePackGenAutoGenUuid.isChecked
        resourcePackCustomIcon = resourcePackGenCustomPackIcon.isChecked

        if(PermissionChecker.checkSelfPermission(context!!,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            PermissionDialog().show(fragmentManager,"PermissionDialog")
        }

        resourcePackGenGenerate.setOnClickListener {
            if(PermissionChecker.checkSelfPermission(context!!,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
                makeToast(context!!,resources.getString(R.string.permission_is_not_granted))
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", context?.packageName, null)
                startActivity(intent)
            } else {
                if(!BedrockUtil(activity!!.packageManager).isInstalled()) {
                    makeSnackBar(view!!,resources.getString(R.string.mcpe_is_not_installed))
                } else {
                    if(resourcePackGenName.text.toString() == "") {
                        makeSnackBar(view!!,resources.getString(R.string.resource_pack_gen_not_input_name))
                    } else {
                        resourcePackName = resourcePackGenName.text.toString()
                        resourcePackDescription = resourcePackGenDescription.text.toString()
                        if(resourcePackAutoGenUUID) {
                            resourcePackHeaderUUID = UUID.randomUUID().toString()
                            resourcePackModuleUUID = UUID.randomUUID().toString()
                        } else {
                            resourcePackHeaderUUID = resourcePackGenHeaderUuid.text.toString()
                            resourcePackModuleUUID = resourcePackGenModuleUuid.text.toString()
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
                            val versionTxtFile = File(FileUtil.getExternalStoragePath()+"BedrockPro/cache/resource/version.txt")
                            if (versionTxtFile.exists()) {
                                if(versionTxtFile.readText() != BedrockUtil(activity!!.packageManager).getVersion()) {
                                    val versionErrorDialog = SimpleDialog.newInstance(resources.getString(R.string.dialog_version_error_title),
                                            resources.getString(R.string.dialog_version_error_message),
                                            resources.getString(R.string.dialog_version_error_positive),resources.getString(R.string.dialog_version_error_negative))
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

        resourcePackGenResourceCache.setOnClickListener {
            resourcePackCache = resourcePackGenResourceCache.isChecked
        }

        resourcePackGenAutoGenUuid.setOnClickListener {
            if(resourcePackGenAutoGenUuid.isChecked){
                resourcePackGenHeaderUuid.text = SpannableStringBuilder("")
                resourcePackGenModuleUuid.text = SpannableStringBuilder("")
                resourcePackGenHeaderUuid.visibility = View.GONE
                resourcePackGenModuleUuid.visibility = View.GONE
                resourcePackAutoGenUUID = true
            } else {
                resourcePackGenHeaderUuid.visibility = View.VISIBLE
                resourcePackGenModuleUuid.visibility = View.VISIBLE
                resourcePackAutoGenUUID = false
            }
        }

        resourcePackGenCustomPackIcon.setOnClickListener {
            if(resourcePackGenCustomPackIcon.isChecked) {
                resourcePackGenPickCustomIcon.visibility = View.VISIBLE
                resourcePackGenCustomPackIconCard.visibility = View.VISIBLE
                resourcePackCustomIcon = true
            } else {
                resourcePackGenPickCustomIcon.visibility = View.GONE
                resourcePackGenCustomPackIconCard.visibility = View.GONE
                resourcePackCustomIcon = false
            }
        }

        resourcePackGenPickCustomIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, resultPickImageFile)
        }

        resourcePackGenSubPackEditor.setOnClickListener {
            val intent = Intent(context, SubpackEditor::class.java)
            intent.putExtra("subPackData",subPackData)
            startActivityForResult(intent,9543)
        }

        resourcePackGenDeleteCache.setOnClickListener {
            if(File(FileUtil.getExternalStoragePath()+"BedrockPro/cache/resource/").exists()) {
                val deleteCacheDialog = SimpleDialog.newInstance(resources.getString(R.string.resource_pack_gen_dialog_delete_cache),resources.getString(R.string.resource_pack_gen_dialog_delete_cache_description))
                deleteCacheDialog.setDialogListener(this)
                deleteCacheDialog.show(fragmentManager,"delete_cache_dialog")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == resultPickImageFile && resultCode == Activity.RESULT_OK) {
            val uri : Uri
            if (data != null) {
                uri = data.data
                resourcePackCustomIconBitmap = UriUtil.getBitmapFromUri(activity!!,uri)
                resourcePackGenCustomPackIconImageView.setImageBitmap(resourcePackCustomIconBitmap)
            }
        } else if(resultCode == 9543) {
            if (data != null) {
                subPackData = data.getSerializableExtra("subPackData") as SubpackData?
            }
        }
    }

    override fun onPositiveClick(tag : String) {
        when (tag) {
            "resource_pack_gen_resolute_check_dialog" -> generateResourcePack()
            "resource_pack_gen_cache_version_error" -> deleteCache(true)
            "delete_cache_dialog" -> deleteCache()
        }
    }

    override fun onNegativeClick(tag : String) {
        when (tag) {
            "resource_pack_gen_cache_version_error" -> resourcePackGenResoluteCheckDialog!!.show(fragmentManager,"resource_pack_gen_resolute_check_dialog")
        }
    }

    private fun deleteCache(dialogShow : Boolean = false) {
        val file = File(FileUtil.getExternalStoragePath()+"BedrockPro/cache/resource/")
        val progress = ProgressDialog()
        progress.show(fragmentManager,"ProgressDialog")
        thread {
            progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_delete_cache)
            file.deleteRecursively()
            progress.dismiss()
            if(dialogShow) {
                resourcePackGenResoluteCheckDialog!!.show(fragmentManager,"resource_pack_gen_resolute_check_dialog")
            }
        }
    }

    private fun generateResourcePack() {
        val mcbeUtil = BedrockUtil(activity!!.packageManager)
        val cacheFolder = FileUtil.getExternalStoragePath() + "BedrockPro/cache/"
        val resourceFolder = cacheFolder+"resource/"
        val assetsFolder = resourceFolder + "assets/resource_packs/vanilla/"
        val outFolder = FileUtil.getExternalStoragePath() + "games/com.mojang/resource_packs/" + resourcePackName + "/"
        FileUtil.createFile(cacheFolder+".nomedia")
        val progress = ProgressDialog()
        progress.show(fragmentManager,"generate_resource_pack_progress_dialog")
        thread {
            progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_message_initial)
            if(FileUtil.getFolderSize(assetsFolder) <= 25000000) {
                progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_message_unzip)
                File(FileUtil.getExternalStoragePath()+"BedrockPro/cache/resource/").deleteRecursively()
                FileUtil.unzip(mcbeUtil.getinstallLocation()!!,resourceFolder,"assets/resource_packs/vanilla/",progress)
                FileUtil.createTxtFile(resourceFolder+"version.txt",mcbeUtil.getVersion()!!)
            }
            progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_copy_resource).format(outFolder)
            File(assetsFolder).copyRecursively(File(outFolder))
            progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_delete_file)
            for (i in 0 until deleteFileList.size) {
                FileUtil.deleteFile(outFolder + deleteFileList[i])
            }
            progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_edit_manifest)
            if(subPackData?.name!!.size <= 1 && subPackData?.name!![0] != "") {
                FileUtil.createDirectory(outFolder+"subpacks")

                for(i in 0 until subPackData?.directory!!.size) {
                    FileUtil.createDirectory(outFolder+"subpacks/"+subPackData?.directory!![i])
                }
                BedrockUtil.editManifest(outFolder + "manifest.json",resourcePackName,resourcePackDescription,resourcePackHeaderUUID,resourcePackModuleUUID,subPackData?.name,subPackData?.directory,subPackData?.memoryTier)
            } else {
                BedrockUtil.editManifest(outFolder + "manifest.json",resourcePackName,resourcePackDescription,resourcePackHeaderUUID,resourcePackModuleUUID)
            }
            if(resourcePackCustomIcon && resourcePackCustomIconBitmap != null) {
                File(outFolder+"pack_icon.png").delete()
                val fileOutputStream = FileOutputStream(File(outFolder+"pack_icon.png"))
                resourcePackCustomIconBitmap?.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream)
            }

            if(!resourcePackCache) {
                progress.message = resources.getString(R.string.resource_pack_gen_dialog_progress_delete_cache)
                File(FileUtil.getExternalStoragePath()+"BedrockPro/cache/resource/").deleteRecursively()
            }
            progress.dismiss()
        }
    }
}