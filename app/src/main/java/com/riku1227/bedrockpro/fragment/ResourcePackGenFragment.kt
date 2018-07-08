package com.riku1227.bedrockpro.fragment


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.google.android.material.textfield.TextInputEditText

import com.riku1227.bedrockpro.R
import com.riku1227.bedrockpro.dialog.PermissionDialog
import kotlinx.android.synthetic.main.fragment_resource_pack_gen.*
import java.util.*

class ResourcePackGenFragment : androidx.fragment.app.Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {
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
                resourcePackGenHeaderUUIDLayout.visibility = View.VISIBLE
                resourcePackGenModuleUUIDLayout.visibility = View.VISIBLE
                resourcePackGenHeaderUUID.visibility = View.VISIBLE
                resourcePackGenModuleUUID.visibility = View.VISIBLE
                resourcePackGenHeaderUUID.setText("")
                resourcePackGenModuleUUID.setText("")
            } else {
                resourcePackGenHeaderUUIDLayout.visibility = View.GONE
                resourcePackGenModuleUUIDLayout.visibility = View.GONE
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
            if(resourcePackGenAutoGenerateUUID.isChecked) {
                resourcePackGenHeaderUUID.setText(UUID.randomUUID().toString())
                resourcePackGenModuleUUID.setText(UUID.randomUUID().toString())
            }

            if(PermissionChecker.checkSelfPermission(context!!,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
                PermissionDialog().show(fragmentManager,"PermissionDialog")
            } else if(checkEditText()) {
            }
        }

        resourcePackGenPickIcon.setOnClickListener {

        }

        resourcePackGenSubPackEditor.setOnClickListener {

        }

        resourcePackGenDeleteCache.setOnClickListener {

        }
    }

    private fun checkEditText() : Boolean {
        var result = true
        for(i in 0 until editTextGroup.childCount) {
            val editTextLayout = editTextGroup.getChildAt(i) as ViewGroup
            val frameLayout = editTextLayout.getChildAt(0) as ViewGroup
            val editText = frameLayout.getChildAt(0) as TextInputEditText
            if(editText.text.toString() == "") {
                editText.error = resources.getString(R.string.not_entered)
                result = false
            }
        }
        return result
    }
}
