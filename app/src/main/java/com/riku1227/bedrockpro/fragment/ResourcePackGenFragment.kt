package com.riku1227.bedrockpro.fragment


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker

import com.riku1227.bedrockpro.R
import com.riku1227.bedrockpro.dialog.PermissionDialog
import kotlinx.android.synthetic.main.fragment_resource_pack_gen.*

class ResourcePackGenFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_resource_pack_gen, container, false)
    }

    override fun onStart() {
        super.onStart()

        if(PermissionChecker.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            PermissionDialog().show(fragmentManager,"PermissionDialog")
        }

        resourcePackGenAutoGenerateUUID.setOnClickListener {
            if(!resourcePackGenAutoGenerateUUID.isChecked) {
                resourcePackGenHeaderUUID.visibility = View.VISIBLE
                resourcePackGenModuleUUID.visibility = View.VISIBLE
            } else {
                resourcePackGenHeaderUUID.visibility = View.GONE
                resourcePackGenModuleUUID.visibility = View.GONE
            }
        }

        resourcePackGenCustomPackIcon.setOnClickListener {
            if(resourcePackGenCustomPackIcon.isChecked) {
                resourcePackGenPickIcon.visibility = View.VISIBLE
                resourcePackGenIconView.visibility = View.VISIBLE
            } else {
                resourcePackGenPickIcon.visibility = View.GONE
                resourcePackGenIconView.visibility = View.GONE
            }
        }

        resourcePackGenGenerate.setOnClickListener {

        }

        resourcePackGenPickIcon.setOnClickListener {

        }

        resourcePackGenSubPackEditor.setOnClickListener {

        }

        resourcePackGenDeleteCache.setOnClickListener {

        }
    }
}
