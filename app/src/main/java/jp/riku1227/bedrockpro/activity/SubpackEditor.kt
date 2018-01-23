package jp.riku1227.bedrockpro.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.riku1227.bedrockpro.R
import jp.riku1227.bedrockpro.adapter.SubpackEditAdapter

import kotlinx.android.synthetic.main.activity_sub_pack_editor.*
import android.content.Intent
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.View
import jp.riku1227.bedrockpro.`object`.SubpackData
import jp.riku1227.bedrockpro.makeToast
import java.lang.reflect.Array


class SubpackEditor : AppCompatActivity() {

    /*private var subPackCardName = arrayListOf<String>()
    private var subPackCardDirectory = arrayListOf<String>()
    private var subPackCardMemoryTier = arrayListOf<String>()
    private var suvPackCardViewHolder = arrayListOf<RecyclerView.ViewHolder>()*/

    private var subPackData : SubpackData? = null

    private var subPackCardAdapter : SubpackEditAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_pack_editor)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activityFinish()
        }

        /*subPackCardName = intent.getStringArrayListExtra("subPackCardName")
        subPackCardDirectory = intent.getStringArrayListExtra("subPackCardDirectory")
        subPackCardMemoryTier = intent.getStringArrayListExtra("subPackCardMemoryTier")*/
        subPackData = intent.getSerializableExtra("subPackData") as SubpackData?

        val layoutManager = LinearLayoutManager(this)

        if(subPackData?.viewHolder?.size == 0) {
            subPackCardAdapter = SubpackEditAdapter(layoutInflater,subPackData?.name!!,subPackData?.directory!!,subPackData?.memoryTier!!)
        } else {
            subPackCardAdapter = SubpackEditAdapter(layoutInflater,subPackData?.name!!,subPackData?.directory!!,subPackData?.memoryTier!!)
        }

        subPackEditorList.setItemViewCacheSize(100)
        subPackEditorList.setHasFixedSize(true)
        subPackEditorList.adapter = subPackCardAdapter
        subPackEditorList.layoutManager = layoutManager
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            activityFinish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun activityFinish() {
        //if(subPackCardAdapter?.getOnBindViewHolderCallCount() == subPackCardAdapter?.itemCount) {
        //subPackData?.viewHolder = subPackCardAdapter!!.getViewHolderList()
        /* for (i in 0 until subPackCardAdapter?.getDeletePositionList()!!.size) {

        } */
        /* subPackCardAdapter?.getDeletePositionList()?.forEach {
            subPackData?.name!!.removeAt(it)
            subPackData?.directory!!.removeAt(it)
            subPackData?.memoryTier!!.removeAt(it)
        } */
            Log.e("TEst","SubPackData: ${subPackData?.name?.size.toString()}")
            Log.e("TEst","SubPackAdapter: ${subPackCardAdapter?.itemCount.toString()}")
            for (i in 0 until subPackCardAdapter?.itemCount!!) {
                val subPackCardViewHolder = subPackCardAdapter?.getViewHolderList()!![i]
                Log.e("TEst","CardName[$i]: ${subPackCardViewHolder?.itemView?.findViewById<TextInputEditText>(R.id.subPackCardName)?.text}")
                if(subPackCardViewHolder != null) {
                    subPackData?.name!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardName).text.toString()
                    subPackData?.directory!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardDirectory).text.toString()
                    subPackData?.memoryTier!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardMemoryTier).text.toString()
                }
            }

        /* for (i in 0 until subPackEditorList.childCount) {
            val subPackCardViewHolder = subPackEditorList.findViewHolderForLayoutPosition(i)
            if(subPackCardViewHolder != null) {
                subPackData?.name!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardName).text.toString()
                subPackData?.directory!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardDirectory).text.toString()
                subPackData?.memoryTier!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardMemoryTier).text.toString()
            }
        }*/
            val intent = Intent()
            /*intent.putExtra("subPackCardName", subPackCardName)
            intent.putExtra("subPackCardDirectory", subPackCardDirectory)
            intent.putExtra("subPackCardMemoryTier", subPackCardMemoryTier)*/
            intent.putExtra("subPackData",subPackData)
            setResult(9543, intent)
            finish()
        /*} else {
            makeToast(baseContext,"一度一番下までスライドしてください")
        }*/
    }
}
