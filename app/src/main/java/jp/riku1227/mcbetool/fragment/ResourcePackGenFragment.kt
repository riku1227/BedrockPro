package jp.riku1227.mcbetool.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    var resourcePackName = ""
    var resourcePackDescription = ""
    var resourcePackHeaderUUID = ""
    var resourcePackModuleUUID = ""

    var resourcePackCache = true
    var resourcePackAutoGenUUID = true
    var resourcePackCustomIcon = false

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
        progress.show(fragmentManager,"ProgressDialog")
        thread {
            makeThreadToast(handler,context,"Start APK unzip...")
            FileUtil.unzip(mcbeUtil.getinstallLocation()!!,resourceFolder)
            makeThreadToast(handler,context,"End APK unzip...")
            progress.dismiss()
        }
    }
}