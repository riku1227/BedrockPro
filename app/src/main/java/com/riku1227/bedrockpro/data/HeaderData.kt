package com.riku1227.bedrockpro.data

data class HeaderData(
        val description: String = "",
        val name: String = "",
        val uuid: String = "",
        val version: ArrayList<Int> = arrayListOf(0, 0, 1),
        val min_engine_version: ArrayList<Int> = arrayListOf(1, 2, 6)
)