package com.espstudio.app.usb

import android.hardware.usb.UsbDevice
import android.os.Build

object UsbHelper {

    fun getDevicePath(device: UsbDevice): String {
        return if (Build.VERSION.SDK_INT >= 29) {
            "/dev/bus/usb/${device.busNumber}/${device.deviceAddress}"
        } else {
            "/dev/bus/usb/${device.vendorId}/${device.productId}"
        }
    }
}
