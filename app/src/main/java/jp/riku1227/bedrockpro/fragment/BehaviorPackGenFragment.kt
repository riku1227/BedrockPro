package jp.riku1227.bedrockpro.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.bedrockpro.R
import kotlinx.android.synthetic.main.fragment_behavior_pack_gen.xml.*


class BehaviorPackGenFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_behavior_pack_gen, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "BehaviorPackGen"

        behaviorPackGenAutoGenUuid.setOnClickListener {
            if(behaviorPackGenAutoGenUuid.isChecked){
                behaviorPackGenHeaderUuid.text = SpannableStringBuilder("")
                behaviorPackGenModuleUuid.text = SpannableStringBuilder("")
                behaviorPackGenHeaderUuid.visibility = View.GONE
                behaviorPackGenModuleUuid.visibility = View.GONE
            } else {
                behaviorPackGenHeaderUuid.visibility = View.VISIBLE
                behaviorPackGenModuleUuid.visibility = View.VISIBLE
            }
        }

        behaviorPackGenCustomPackIcon.setOnClickListener {
            if(behaviorPackGenCustomPackIcon.isChecked) {
                behaviorPackGenPickCustomIcon.visibility = View.VISIBLE
                behaviorPackGenCustomPackIconCard.visibility = View.VISIBLE
            } else {
                behaviorPackGenPickCustomIcon.visibility = View.GONE
                behaviorPackGenCustomPackIconCard.visibility = View.GONE
            }
        }
    }
}
