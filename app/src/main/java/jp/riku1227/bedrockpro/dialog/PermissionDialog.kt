package jp.riku1227.bedrockpro.dialog

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import jp.riku1227.bedrockpro.R

class PermissionDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val res = resources
        val dialog =
                AlertDialog.Builder(activity!!.baseContext)
                .setTitle(res.getString(R.string.dialog_grant_permission))
                .setMessage(res.getString(R.string.dialog_grant_external_storage_message))
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                })
        this.isCancelable = false
        return dialog.create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}