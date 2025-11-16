package com.espstudio.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.espstudio.app.databinding.ActivitySettingsBinding
import com.espstudio.app.utils.Prefs

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        loadPrefs()
        setupEvents()
    }

    private fun loadPrefs() {
        binding.switchDarkMode.isChecked = true  // Always dark
        binding.switchFormatOnSave.isChecked = 
            Prefs.getFormatOnSave(this)
    }

    private fun setupEvents() {

        binding.switchFormatOnSave.setOnCheckedChangeListener { _, value ->
            Prefs.setFormatOnSave(this, value)
        }
    }
}
