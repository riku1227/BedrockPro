package jp.riku1227.bedrockpro.adapter

import android.support.design.widget.TextInputEditText
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jp.riku1227.bedrockpro.R


class SubpackEditAdapter(layoutInflater: LayoutInflater, subPackNameList : ArrayList<String>, subPackDirectoryList : ArrayList<String>, subPackMemoryTier : ArrayList<String>) : RecyclerView.Adapter<SubpackEditAdapter.ViewHolder>() {

    private var subPackCardNameList = arrayListOf<String>()
    private var subPackCardDirectoryList = arrayListOf<String>()
    private var subPackCardMemoryTier = arrayListOf<String>()
    private var viewHolderList = arrayListOf<ViewHolder?>()

    private var mInflater : LayoutInflater? = null
    private var mRecyclerView : RecyclerView? = null

    init {
        mInflater = layoutInflater
        subPackCardNameList = subPackNameList
        subPackCardDirectoryList = subPackDirectoryList
        subPackCardMemoryTier = subPackMemoryTier
        for(i in 0 until itemCount) {
            viewHolderList.add(null)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var subPackName : TextInputEditText = v.findViewById(R.id.subPackCardName)
        var subPackDirectory : TextInputEditText = v.findViewById(R.id.subPackCardDirectory)
        var subPackMemoryTier : TextInputEditText = v.findViewById(R.id.subPackCardMemoryTier)
        var subPackAddButton : Button = v.findViewById(R.id.subPackCardAdd)
        var subPackDeleteButton : Button = v.findViewById(R.id.subPackDelete)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        mRecyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : ViewHolder {
        return ViewHolder(mInflater!!.inflate(R.layout.card_subpack, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if(viewHolderList.size > position) {
            viewHolderList[position] = holder
        } else {
            viewHolderList.add(holder)
        }
        holder?.subPackName?.setText(subPackCardNameList[position])
        holder?.subPackDirectory?.setText(subPackCardDirectoryList[position])
        holder?.subPackMemoryTier?.setText(subPackCardMemoryTier[position])

        holder?.subPackName!!.requestFocus()

        holder.subPackAddButton.setOnClickListener {
            subPackCardNameList.add("")
            subPackCardDirectoryList.add("")
            subPackCardMemoryTier.add("")
            notifyItemInserted(itemCount)
            mRecyclerView?.scrollToPosition(itemCount - 1)
        }

        holder.subPackDeleteButton.setOnClickListener {
            subPackCardNameList.removeAt(holder.layoutPosition)
            subPackCardDirectoryList.removeAt(holder.layoutPosition)
            subPackCardMemoryTier.removeAt(holder.layoutPosition)
            viewHolderList.removeAt(holder.layoutPosition)
            notifyItemRemoved(holder.layoutPosition)
        }
    }

    override fun getItemCount(): Int {
        return subPackCardNameList.size
    }

    fun getViewHolderList() : ArrayList<ViewHolder?> {
        return viewHolderList
    }
}