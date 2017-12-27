package jp.riku1227.bedrockpro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.riku1227.bedrockpro.R


class BehaviorPackGenFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_behavior_pack_gen, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "BehaviorPackGen"
    }
}
