package com.espstudio.app.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.espstudio.app.databinding.ActivitySketchManagerBinding
import com.espstudio.app.build.ArduinoConfig
import com.espstudio.app.R
import java.io.File

class SketchManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySketchManagerBinding
    private lateinit var adapter: SketchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySketchManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupList()
        loadSketches()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_new_sketch -> {
                    showCreateDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupList() {
        adapter = SketchListAdapter(
            onClick = { file -> openSketch(file) },
            onDelete = { file -> deleteSketch(file) }
        )

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        binding.recycler.itemAnimator?.apply { 
            addDuration = 150
            removeDuration = 150 
        }
    }

    private fun loadSketches() {
        val dir = ArduinoConfig.sketchesDir()
        if (!dir.exists()) dir.mkdirs()

        adapter.update(dir.listFiles()?.filter { it.isDirectory } ?: emptyList())
        binding.emptyView.visibility =
            if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun openSketch(file: File) {
        val ino = file.listFiles()?.firstOrNull { it.extension == "ino" } ?: return

        val intent = Intent(this, EditorActivity::class.java)
        intent.putExtra("path", ino.absolutePath)
        startActivity(intent)
    }

    private fun deleteSketch(file: File) {
        AlertDialog.Builder(this)
            .setTitle("Delete Sketch")
            .setMessage("Delete ${file.name}?")
            .setPositiveButton("Delete") { _, _ ->
                file.deleteRecursively()
                loadSketches()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showCreateDialog() {
        val input = EditText(this)
        input.hint = "Sketch name"

        AlertDialog.Builder(this)
            .setTitle("New Sketch")
            .setView(input)
            .setPositiveButton("Create") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) createSketch(name)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createSketch(name: String) {
        val dir = File(ArduinoConfig.sketchesDir(), name)
        dir.mkdirs()

        val ino = File(dir, "$name.ino")
        ino.writeText("// Sketch: $name\n\nvoid setup() {}\nvoid loop() {}\n")

        loadSketches()
    }
}
