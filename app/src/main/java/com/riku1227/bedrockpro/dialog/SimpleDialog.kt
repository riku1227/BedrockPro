package com.riku1227.bedrockpro.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class SimpleDialog : DialogFragment() {
    var listener: DialogListener? = null

    companion object {
        fun newInstance(title : String,message : String, positiveButtonName : String = "OK", negativeButtonName : String = "Cancel") : SimpleDialog {
            val simple = SimpleDialog()
            val bundle = Bundle()
            bundle.putString("title",title)
            bundle.putString("message", message)
            bundle.putString("positiveButtonName", positiveButtonName)
            bundle.putString("negativeButtonName", negativeButtonName)
            simple.arguments = bundle
            return simple
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setTitle(arguments?.getString("title"))
                .setMessage(arguments?.getString("message"))
                .setPositiveButton(arguments?.getString("positiveButtonName")) { _, _ ->
                    listener?.onPositiveClick(tag!!)
                    dismiss()
                }
                .setNegativeButton(arguments?.getString("negativeButtonName")) { _, _ ->
                    listener?.onNegativeClick(tag!!)
                    dismiss()
                }
                .create()
    }
}