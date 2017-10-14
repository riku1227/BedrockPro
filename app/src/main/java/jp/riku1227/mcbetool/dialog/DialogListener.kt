package jp.riku1227.mcbetool.dialog

import java.util.*

interface DialogListener : EventListener {

    fun onPositiveClick()

    fun onNegativeClick()
}