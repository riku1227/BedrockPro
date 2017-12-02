package jp.riku1227.bedrockpro.dialog

import java.util.*

interface DialogListener : EventListener {

    fun onPositiveClick(tag : String)

    fun onNegativeClick(tag : String)
}