package com.espstudio.app.ui

import android.content.Context
import android.hardware.usb.UsbDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.espstudio.app.R

class UsbListAdapter(
    private val context: Context,
    private val devices: List<UsbDevice>,
    private val onDeviceClick: (UsbDevice) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = devices.size

    override fun getItem(position: Int): UsbDevice = devices[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        val device = devices[position]
        
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = device.deviceName
        text2.text = "VID: ${device.vendorId} PID: ${device.productId}"

        view.setOnClickListener {
            onDeviceClick(device)
        }

        return view
    }
}
