package com.espstudio.app.utils

import android.app.AlertDialog
import android.content.Context
import java.io.File

class FilePickerDialog(
    private val context: Context,
    private val root: File,
    private val onSelect: (File) -> Unit
) {

    private var current = root

    fun show() {
        showDialog()
    }

    private fun showDialog() {
        val items = getItems()
        AlertDialog.Builder(context)
            .setTitle(current.absolutePath)
            .setItems(items) { _, which ->
                handleSelect(which)
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun getItems(): Array<String> {
        return current.list()?.sorted()?.toTypedArray() ?: emptyArray()
    }

    private fun handleSelect(which: Int) {
        val list = current.listFiles()?.sorted() ?: return
        val file = list[which]

        if (file.isDirectory) {
            current = file
            showDialog()
        } else {
            onSelect(file)
        }
    }
}
