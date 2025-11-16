package com.espstudio.app.ui

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.espstudio.app.databinding.ActivityBuildConsoleBinding
import com.espstudio.app.build.BuildService
import kotlinx.coroutines.launch
import java.io.File

class BuildConsoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuildConsoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildConsoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sketchPath = intent.getStringExtra("sketch") ?: return

        lifecycleScope.launch {
            binding.console.text = "Building...\n"
            val out = BuildService.compileSketch(this@BuildConsoleActivity, sketchPath)
            binding.console.text = out
        }

        binding.btnFlash.setOnClickListener {
            val sketch = intent.getStringExtra("sketch")!!
            val bin = File(sketch, "build/esp32.esp32.esp32/firmware.bin")

            val i = Intent(this, UsbDeviceSelectActivity::class.java)
            i.putExtra("bin", bin.absolutePath)
            startActivity(i)
        }

        binding.btnExport.setOnClickListener {
            val log = binding.console.text.toString()
            val file = File(
                "/storage/emulated/0/ESPStudio/logs/",
                "build_log_${System.currentTimeMillis()}.txt"
            )
            file.parentFile?.mkdirs()
            file.writeText(log)
            Toast.makeText(this, "Saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        }
    }
}
