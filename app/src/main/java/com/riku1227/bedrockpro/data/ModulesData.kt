package com.riku1227.bedrockpro.data

data class ModulesData(
        val description: String = "",
        val type: String = "",
        val uuid: String = "",
        val version: ArrayList<Int> = arrayListOf(0, 0, 1)
)