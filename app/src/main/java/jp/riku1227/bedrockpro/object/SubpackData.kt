package jp.riku1227.bedrockpro.`object`

import java.io.Serializable

data class SubpackData(
        var name : ArrayList<String>?,
        var directory : ArrayList<String>?,
        var memoryTier : ArrayList<String>?
) : Serializable