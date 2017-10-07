package jp.riku1227.mcbetool.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.ScrollView
import jp.riku1227.mcbetool.*
import jp.riku1227.mcbetool.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name,
                R.string.app_version_code)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        val homeFragment = HomeFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flameLayout,homeFragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        /* val id = item.itemId
        val scroll : ScrollView = findViewById(R.id.main_scroll)
        scroll.removeAllViews()
        when (id) {
            R.id.drawer_home -> layoutInflater.inflate(R.layout.activity_main,scroll)
            //R.id.drawer_resource_pack_gen -> layoutInflater.inflate(R.layout.activity_resource_pack_gen,scroll)

            else -> makeToast(this,"Test")
        } */
        makeToast(this,"Test")
        drawerLayout.closeDrawers()
        return true
    }
}