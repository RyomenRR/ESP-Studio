package com.espstudio.app.ui

import android.hardware.usb.UsbManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.espstudio.app.databinding.ActivityUsbSelectBinding
import com.espstudio.app.usb.UsbHelper

class UsbDeviceSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsbSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsbSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usb = getSystemService(USB_SERVICE) as UsbManager
        val list = usb.deviceList.values.toList()
        val binPath = intent.getStringExtra("bin") ?: return

        binding.listDevices.adapter =
            UsbListAdapter(this, list) { device ->
                val port = UsbHelper.getDevicePath(device)

                val i = Intent(this, FlashConsoleActivity::class.java)
                i.putExtra("port", port)
                i.putExtra("bin", binPath)
                startActivity(i)
            }
    }
}
