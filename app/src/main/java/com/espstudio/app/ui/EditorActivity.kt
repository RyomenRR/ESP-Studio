package com.espstudio.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.espstudio.app.databinding.ActivityEditorBinding
import com.espstudio.app.R
import com.espstudio.app.utils.Prefs
import io.github.rosemoe.sora.editor.CodeEditor
import io.github.rosemoe.sora.langs.cpp.CppLanguage
import java.io.File

class EditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditorBinding
    private var currentFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEditor()
        setupToolbar()

        // Receive file path from MainActivity
        intent.getStringExtra("path")?.let {
            loadFile(File(it))
        }
    }

    private fun setupEditor() {
        binding.editor.setEditorLanguage(CppLanguage())
        binding.editor.isLineNumberEnabled = true
        binding.editor.isWordwrap = false
        binding.editor.colorScheme.isDark = true
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_save -> {
                    saveFile()
                    true
                }

                R.id.action_open -> {
                    openFilePicker()
                    true
                }

                R.id.action_format -> {
                    formatCode()
                    true
                }

                R.id.action_build -> {
                    val intent = Intent(this, BuildConsoleActivity::class.java)
                    intent.putExtra("sketch", currentFile!!.parent!!)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFile(file: File) {
        if (!file.exists()) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
            return
        }

        currentFile = file
        binding.toolbar.title = file.name
        binding.editor.setText(file.readText())
        
        Prefs.saveLastSketch(this, file.absolutePath)
    }

    private fun saveFile() {
        val file = currentFile ?: return

        if (Prefs.getFormatOnSave(this)) {
            formatCode()
        }

        try {
            file.writeText(binding.editor.text.toString())
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun formatCode() {
        val formatted = binding.editor.text.toString()
            .replace(Regex("(?m)^[ \\t]+"), "") // basic indentation cleanup

        binding.editor.setText(formatted)
        Toast.makeText(this, "Formatted", Toast.LENGTH_SHORT).show()
    }

    private fun openFilePicker() {
        val dialog = FilePickerDialog(
            this,
            File("/storage/emulated/0/ArduinoCLI/projects/")
        ) { selected ->
            loadFile(selected)
        }
        dialog.show()
    }
}
