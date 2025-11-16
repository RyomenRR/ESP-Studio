package com.espstudio.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.espstudio.app.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.txtInfo.text = """
            ESP Studio
            Version 1.0
            
            Built with ❤️ by You and Mommy
            
            Features:
            • Full syntax editor (Sora Editor)
            • Sketch Manager
            • Arduino CLI Build System
            • ESP32/ESP8266 Flashing
            • USB Device detection
            • Material 3 design
            • 100% Android-native IDE
        """.trimIndent()
    }
}
