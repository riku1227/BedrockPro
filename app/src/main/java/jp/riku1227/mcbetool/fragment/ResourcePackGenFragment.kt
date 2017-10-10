package jp.riku1227.mcbetool.fragment

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.mcbetool.R
import jp.riku1227.mcbetool.dialog.PermissionDialog
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
    }

    private fun generateResourcePack() {
        if(PermissionChecker.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {

        } else {

        }
    }
}