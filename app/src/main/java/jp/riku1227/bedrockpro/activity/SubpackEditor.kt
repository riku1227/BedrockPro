package jp.riku1227.bedrockpro.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.riku1227.bedrockpro.R
import jp.riku1227.bedrockpro.adapter.SubpackEditAdapter

import kotlinx.android.synthetic.main.activity_sub_pack_editor.*
import android.content.Intent
import android.support.design.widget.TextInputEditText
import android.view.KeyEvent


class SubpackEditor : AppCompatActivity() {

    private var subPackCardName = arrayListOf<String>()
    private var subPackCardDirectory = arrayListOf<String>()
    private var subPackCardMemoryTier = arrayListOf<String>()

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

        subPackCardName = intent.getStringArrayListExtra("subPackCardName")
        subPackCardDirectory = intent.getStringArrayListExtra("subPackCardDirectory")
        subPackCardMemoryTier = intent.getStringArrayListExtra("subPackCardMemoryTier")

        subPackCardAdapter = SubpackEditAdapter(layoutInflater,subPackCardName,subPackCardDirectory,subPackCardMemoryTier)

        subPackEditorList.setHasFixedSize(true)
        subPackEditorList.adapter = subPackCardAdapter
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
                subPackCardName[i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardName).text.toString()
                subPackCardDirectory[i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardDirectory).text.toString()
                subPackCardMemoryTier[i] = subPackCardViewHolder.itemView.findViewById<TextInputEditText>(R.id.subPackCardMemoryTier).text.toString()
            }
        }
        val intent = Intent()
        intent.putExtra("subPackCardName", subPackCardName)
        intent.putExtra("subPackCardDirectory", subPackCardDirectory)
        intent.putExtra("subPackCardMemoryTier", subPackCardMemoryTier)
        setResult(9543, intent)
        finish()
    }

}
