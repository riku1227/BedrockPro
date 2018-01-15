package jp.riku1227.bedrockpro.adapter

import android.support.design.widget.TextInputEditText
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jp.riku1227.bedrockpro.R


class SubpackEditAdapter(inflater: LayoutInflater, subPackNameList : ArrayList<String>, subPackDirectoryList : ArrayList<String>, subPackMemoryTier : ArrayList<String>) : RecyclerView.Adapter<SubpackEditAdapter.ViewHolder>() {

    private var subPackCardNameList = arrayListOf<String>()
    private var subPackCardDirectoryList = arrayListOf<String>()
    private var subPackCardMemoryTier = arrayListOf<String>()

    private var mInflater : LayoutInflater? = null

    private val viewHolderList = arrayListOf<ViewHolder>()

    init {
        mInflater = inflater
        subPackCardNameList = subPackNameList
        subPackCardDirectoryList = subPackDirectoryList
        subPackCardMemoryTier = subPackMemoryTier
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var subPackName : TextInputEditText
        var subPackDirectory : TextInputEditText
        var subPackMemoryTier : TextInputEditText
        var subPackAddButton : Button
        var subPackDeleteButton : Button

        init {
            subPackName = v.findViewById(R.id.subPackCardName)
            subPackDirectory = v.findViewById(R.id.subPackCardDirectory)
            subPackMemoryTier = v.findViewById(R.id.subPackCardMemoryTier)
            subPackAddButton = v.findViewById(R.id.subPackCardAdd)
            subPackDeleteButton = v.findViewById(R.id.subPackDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : ViewHolder {
        val viewHolder = ViewHolder(mInflater!!.inflate(R.layout.card_subpack, parent, false))
        viewHolderList.add(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.subPackName?.setText(subPackCardNameList[position])
        holder?.subPackDirectory?.setText(subPackCardDirectoryList[position])
        holder?.subPackMemoryTier?.setText(subPackCardMemoryTier[position])

        holder?.subPackAddButton?.setOnClickListener {
            subPackCardNameList.add("")
            subPackCardDirectoryList.add("")
            subPackCardMemoryTier.add("")
            notifyItemInserted(itemCount)
            Log.e("Test",subPackCardNameList.size.toString())
        }

        holder?.subPackDeleteButton?.setOnClickListener {
            subPackCardNameList.removeAt(holder?.layoutPosition)
            subPackCardDirectoryList.removeAt(holder?.layoutPosition)
            subPackCardMemoryTier.removeAt(holder?.layoutPosition)
            Log.e("Tag",holder?.layoutPosition.toString())
            Log.e("Tag",itemCount.toString())
            notifyItemRemoved(holder?.layoutPosition)
        }
    }

    override fun getItemCount(): Int {
        return subPackCardNameList.size
    }

    fun getViewHolderList() : ArrayList<ViewHolder> {
        return viewHolderList
    }
}