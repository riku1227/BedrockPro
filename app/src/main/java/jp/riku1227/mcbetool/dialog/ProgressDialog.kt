package jp.riku1227.mcbetool.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import jp.riku1227.mcbetool.R

class ProgressDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        this.isCancelable = false
        dialog.setView(view)
        return dialog.create()
    }
}