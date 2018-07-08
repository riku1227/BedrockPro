package com.riku1227.bedrockpro.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.riku1227.bedrockpro.R
import kotlin.concurrent.thread

class ProgressDialog : DialogFragment() {
    var finish = false
    var progressBarCount = 0
    var progressMessage = ""
    var progressLog = ""
    var progressLogVisibility = true
    var progressSubMessage = ""
    var progressSubMessageVisibility = true

    private var handler : Handler? = null
    private var maxProgressBar = 5

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity!!)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        handler = Handler()
        this.isCancelable = false
        dialog.setView(view)
        return dialog.create()
    }

    override fun onResume() {
        super.onResume()
        val progressText = dialog.findViewById<TextView>(R.id.progressText)
        val progressLogText = dialog.findViewById<TextView>(R.id.logText)
        val progressSubText = dialog.findViewById<TextView>(R.id.progressSubText)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = maxProgressBar
        thread {
            while (!finish) {
                handler?.post {
                    progressText.text = progressMessage
                    progressBar.progress = progressBarCount
                    if (progressLogVisibility) {
                        progressLogText.visibility = View.VISIBLE
                    } else {
                        progressLogText.visibility = View.GONE
                    }
                    if(progressSubMessageVisibility) {
                        progressSubText.visibility = View.VISIBLE
                    } else {
                        progressSubText.visibility = View.GONE
                    }
                    progressLogText.text = progressLog
                    progressSubText.text = progressSubMessage

                }
                Thread.sleep(10)
            }
        }
    }

    fun setMaxProgress(num: Int) {
        maxProgressBar = num
    }
}