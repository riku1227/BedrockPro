package com.riku1227.bedrockpro.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.riku1227.bedrockpro.R
import com.riku1227.bedrockpro.fragment.HomeFragment
import com.riku1227.bedrockpro.fragment.ResourcePackGenFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null) {
            val homeFragment = HomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout,homeFragment,"HomeFragment")
            fragmentTransaction.commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when(item.itemId) {
            R.id.drawer_resource_pack_gen -> replaceFragment(fragmentTransaction, ResourcePackGenFragment(), "ResourcePackGenFragment")
            else -> Toast.makeText(baseContext, "Unimplemented", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawers()
        return true
    }

    private fun replaceFragment(fragmentTransaction : FragmentTransaction, fragment : Fragment, name : String) {
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
