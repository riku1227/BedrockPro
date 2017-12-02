package jp.riku1227.bedrockpro.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import jp.riku1227.bedrockpro.R
import kotlin.concurrent.thread

class ProgressDialog : DialogFragment() {

    var finished = false
    var message = ""

    private var handler : Handler? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        handler = Handler()
        this.isCancelable = false
        dialog.setView(view)
        return dialog.create()
    }

    override fun onResume() {
        super.onResume()
        val progressText = dialog.findViewById<TextView>(R.id.progress_text)
        thread {
            while (!finished) {
                handler?.post {
                    progressText.text = message
                }
                Thread.sleep(100)
            }
        }
    }
}