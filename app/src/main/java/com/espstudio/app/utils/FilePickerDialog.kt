package com.espstudio.app.utils

import android.app.AlertDialog
import android.content.Context
import java.io.File

class FilePickerDialog(
    context: Context,
    private val root: File,
    private val onSelect: (File) -> Unit
) {

    private var current = root

    private val dialog: AlertDialog =
        AlertDialog.Builder(context)
            .setTitle("Select File")
            .setItems(getItems()) { _, which ->
                handleSelect(which)
            }.setNegativeButton("Close", null)
            .create()

    fun show() = dialog.show()

    private fun getItems(): Array<String> {
        return current.list()?.sorted()?.toTypedArray() ?: emptyArray()
    }

    private fun handleSelect(which: Int) {
        val list = current.listFiles()?.sorted() ?: return
        val file = list[which]

        if (file.isDirectory) {
            current = file
            dialog.setTitle(current.absolutePath)
            dialog.setItems(getItems()) { _, i -> handleSelect(i) }
            dialog.show()
        } else {
            onSelect(file)
            dialog.dismiss()
        }
    }
}
