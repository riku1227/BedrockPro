package fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riku1227.bedrockpro.R

class HomeFragment : android.support.v4.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onStart() {
        super.onStart()
    }
}