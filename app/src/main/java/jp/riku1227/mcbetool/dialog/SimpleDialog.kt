package jp.riku1227.mcbetool.dialog

import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog


class SimpleDialog : DialogFragment() {
    var listener : DialogListener? = null

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

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(arguments.getString("title"))
                .setMessage(arguments.getString("message"))
                .setPositiveButton(arguments.getString("positiveButtonName"),DialogInterface.OnClickListener { dialogInterface, i ->
                    listener?.onPositiveClick(tag)
                    dismiss()
                })
                .setNegativeButton(arguments.getString("negativeButtonName"),DialogInterface.OnClickListener {dialogInterface, i ->
                    listener?.onNegativeClick(tag)
                    dismiss()
                })
                .create()
    }

    fun setDialogListener(listener: DialogListener) {
        this.listener = listener
    }

    fun removeDialogListener() {
        this.listener = null
    }
}