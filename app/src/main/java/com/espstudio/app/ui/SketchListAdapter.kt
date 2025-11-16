package com.espstudio.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.espstudio.app.databinding.ItemSketchBinding
import java.io.File

class SketchListAdapter(
    private val onClick: (File) -> Unit,
    private val onDelete: (File) -> Unit
) : RecyclerView.Adapter<SketchListAdapter.VH>() {

    private var items: List<File> = emptyList()

    fun update(newList: List<File>) {
        items = newList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemSketchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(file: File) {
            binding.sketchName.text = file.name

            binding.root.setOnClickListener { onClick(file) }
            binding.btnDelete.setOnClickListener { onDelete(file) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSketchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }
}
