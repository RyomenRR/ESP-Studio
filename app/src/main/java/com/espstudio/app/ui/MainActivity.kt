package com.espstudio.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.espstudio.app.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import com.espstudio.app.R
import com.espstudio.app.utils.Prefs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.topAppBar)

        // Set hamburger icon to open drawer
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // Drawer item actions
        setupDrawer()
    }

    private fun setupDrawer() {
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_editor -> {
                    startActivity(Intent(this, SketchManagerActivity::class.java))
                }

                R.id.nav_sketches -> {
                    startActivity(Intent(this, SketchManagerActivity::class.java))
                }

                R.id.nav_build -> {
                    val intent = Intent(this, BuildConsoleActivity::class.java)
                    intent.putExtra("sketch", lastOpenedSketchPath())
                    startActivity(intent)
                }

                R.id.nav_flash -> {
                    startActivity(Intent(this, UsbDeviceSelectActivity::class.java))
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }

                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun lastOpenedSketchPath(): String {
        return Prefs.getLastSketch(this) ?: ""
    }
}
