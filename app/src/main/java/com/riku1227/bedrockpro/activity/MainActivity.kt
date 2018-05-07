package com.riku1227.bedrockpro.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.riku1227.bedrockpro.R
import fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
            fragmentTransaction.replace(R.id.flameLayout,homeFragment,"HomeFragment")
            fragmentTransaction.commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(baseContext, "Unimplemented", Toast.LENGTH_LONG).show()
        drawerLayout.closeDrawers()
        return true
    }
}
