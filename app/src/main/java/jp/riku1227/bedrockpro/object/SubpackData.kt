package jp.riku1227.bedrockpro.`object`

import jp.riku1227.bedrockpro.adapter.SubpackEditAdapter
import java.io.Serializable

data class SubpackData(
        var name : ArrayList<String>?,
        var directory : ArrayList<String>?,
        var memoryTier : ArrayList<String>?,
        var viewHolder : ArrayList<SubpackEditAdapter.ViewHolder>?
) : Serializable