package jp.riku1227.bedrockpro.activity

import android.Manifest
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.PermissionChecker
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import jp.riku1227.bedrockpro.*
import jp.riku1227.bedrockpro.dialog.PermissionDialog
import jp.riku1227.bedrockpro.fragment.BehaviorPackGenFragment
import jp.riku1227.bedrockpro.fragment.HomeFragment
import jp.riku1227.bedrockpro.fragment.ResourcePackGenFragment
import jp.riku1227.bedrockpro.util.FileUtil
import jp.riku1227.bedrockpro.util.BedrockUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.concurrent.thread


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

        if(savedInstanceState == null) {
            val homeFragment = HomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flameLayout,homeFragment,"HomeFragment")
            fragmentTransaction.commit()
        }

        shortcutAction()
    }

    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        val id = item.itemId
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (id) {
            R.id.drawer_home -> replaceFragment(fragmentTransaction,HomeFragment(),"HomeFragment")
            R.id.drawer_resource_pack_gen -> replaceFragment(fragmentTransaction,ResourcePackGenFragment(),"ResourcePackGenFragment")
            R.id.drawer_util_backup_apk -> backupAPK()
            R.id.drawer_behavior_pack_gen -> replaceFragment(fragmentTransaction,BehaviorPackGenFragment(),"BehaviorPackGenFragment")
        }
        drawerLayout.closeDrawers()
        return true
    }

    private fun replaceFragment(fragmentTransaction : FragmentTransaction, fragment : Fragment, name : String) {
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
        fragmentTransaction.replace(R.id.flameLayout,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun shortcutAction() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val hasExtra = intent.hasExtra("shortcut_id")
        if(!hasExtra) {
            return
        }

        val shortcutId = intent.getStringExtra("shortcut_id")
        when(shortcutId) {
            "ResourcePackGenFragment" -> replaceFragment(fragmentTransaction,ResourcePackGenFragment(),"ResourcePackGenFragment")
            "BehaviorPackGenFragment" -> replaceFragment(fragmentTransaction,BehaviorPackGenFragment(),"BehaviorPackGenFragment")
        }
    }

    private fun backupAPK() {
        val mcutil = BedrockUtil(packageManager)
        if(PermissionChecker.checkSelfPermission(baseContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            PermissionDialog().show(supportFragmentManager,"PermissionDialog")
        } else {
            if(mcutil.isInstalled()) {
                val handler = Handler()
                val outFolder = FileUtil.getExternalStoragePath() + "BedrockPro/apk/"
                val outFile = outFolder + "["+mcutil.getVersion()+"]"+"Minecraft.apk"
                File(outFolder).mkdirs()
                makeToast(baseContext,resources.getString(R.string.backup_apk_start))
                thread {
                    FileUtil.copyFile(mcutil.getinstallLocation()!!,outFile)
                    makeThreadToast(handler,baseContext,resources.getString(R.string.backup_apk_end))
                    Thread.sleep(3000)
                    makeThreadToast(handler,baseContext,resources.getString(R.string.backup_apk_to).format(outFile))
                }
            } else {
                makeSnackBar(findViewById(R.id.flameLayout),resources.getString(R.string.mcpe_is_not_installed))
            }
        }
    }
}