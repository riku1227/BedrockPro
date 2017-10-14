package jp.riku1227.mcbetool.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.PermissionChecker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.mcbetool.R
import jp.riku1227.mcbetool.dialog.PermissionDialog
import jp.riku1227.mcbetool.dialog.ProgressDialog
import jp.riku1227.mcbetool.makeSnackBar
import jp.riku1227.mcbetool.makeToast
import jp.riku1227.mcbetool.util.MCBEUtil
import kotlinx.android.synthetic.main.fragment_resource_pack_gen.*

class ResourcePackGenFragment : android.support.v4.app.Fragment() {

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

        val pm : PackageManager = activity.packageManager
        val mcbeUtil = MCBEUtil(pm)

        if(PermissionChecker.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            PermissionDialog().show(fragmentManager,"PermissionDialog")
        }

        resource_pack_gen_generate.setOnClickListener {
            generateResourcePack()
        }

        resource_pack_gen_resource_cache.setOnClickListener {
            resourcePackCache = resource_pack_gen_resource_cache.isChecked
        }

        resource_pack_gen_auto_gen_uuid.setOnClickListener {
            if(resource_pack_gen_auto_gen_uuid.isChecked){
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

    private fun generateResourcePack() {
        if(PermissionChecker.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            makeToast(context,resources.getString(R.string.permission_is_not_granted))
        } else {
            val progress = ProgressDialog()
            progress.show(fragmentManager,"ProgressDialog")
        }
    }
}