package com.espstudio.app.usb

import android.hardware.usb.UsbDevice

object UsbHelper {

    fun getDevicePath(device: UsbDevice): String {
        // Use device name which is typically /dev/bus/usb/XXX/YYY
        return device.deviceName
    }
}
