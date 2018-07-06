package com.riku1227.bedrockpro.dialog

import android.Manifest
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.riku1227.bedrockpro.R

class PermissionDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val res = resources
        val dialog =
                AlertDialog.Builder(activity!!)
                        .setTitle(res.getString(R.string.dialog_grant_permission))
                        .setMessage(res.getString(R.string.dialog_grant_external_storage_message))
                        .setPositiveButton("OK") { _, _ ->
                            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                        }
        this.isCancelable = false
        return dialog.create()
    }
}