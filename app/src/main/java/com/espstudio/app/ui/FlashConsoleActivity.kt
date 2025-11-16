package com.espstudio.app.ui

import android.hardware.usb.UsbDevice
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.espstudio.app.databinding.ActivityFlashConsoleBinding
import com.espstudio.app.build.FlashService
import kotlinx.coroutines.launch
import java.io.File

class FlashConsoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlashConsoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashConsoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val devicePath = intent.getStringExtra("port") ?: return
        val binPath = intent.getStringExtra("bin") ?: return

        binding.console.text = "Flashing...\n"

        lifecycleScope.launch {
            val out = FlashService.flashFirmware(this@FlashConsoleActivity, devicePath, binPath)
            binding.console.text = out
        }

        binding.btnExport.setOnClickListener {
            val log = binding.console.text.toString()
            val file = File(
                "/storage/emulated/0/ESPStudio/logs/",
                "flash_log_${System.currentTimeMillis()}.txt"
            )
            file.parentFile?.mkdirs()
            file.writeText(log)
            Toast.makeText(this, "Saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        }
    }
}
