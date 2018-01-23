package jp.riku1227.bedrockpro.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.riku1227.bedrockpro.R
import jp.riku1227.bedrockpro.adapter.SubpackEditAdapter

import kotlinx.android.synthetic.main.activity_sub_pack_editor.*
import android.content.Intent
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import jp.riku1227.bedrockpro.`object`.SubpackData


class SubpackEditor : AppCompatActivity() {
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

        subPackData = intent.getSerializableExtra("subPackData") as SubpackData?

        val layoutManager = LinearLayoutManager(this)

        subPackCardAdapter = SubpackEditAdapter(layoutInflater,subPackData?.name!!,subPackData?.directory!!,subPackData?.memoryTier!!)

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
        for (i in 0 until subPackCardAdapter?.itemCount!!) {
            val subPackCardViewHolder = subPackCardAdapter?.getViewHolderList()!![i]
            if(subPackCardViewHolder != null) {
                subPackData?.name!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardName).text.toString()
                subPackData?.directory!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardDirectory).text.toString()
                subPackData?.memoryTier!![i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardMemoryTier).text.toString()
            }
        }
        val intent = Intent()
        intent.putExtra("subPackData",subPackData)
        setResult(9543, intent)
        finish()
    }
}
