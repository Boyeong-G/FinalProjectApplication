package com.example.finalprojectapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectapplication.databinding.CommuRecyclerviewBinding
import com.example.finalprojectapplication.databinding.ItemRecyclerviewBinding

class CommuViewHolder(val binding: CommuRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CommuData) {
        binding.root.setOnClickListener{
            Intent(binding.root.context, QresultActivity::class.java).apply{
                putExtra("docId", data.docId.toString())
                putExtra("title", data.title.toString())
                putExtra("content", data.content.toString())
                putExtra("date", data.date_time.toString())
                putExtra("email", data.email.toString())
            }.run{ binding.root.context.startActivity(this) }
        }
    }
}
class CommuAdapter (val itemList: MutableList<CommuData>): RecyclerView.Adapter<CommuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommuViewHolder {
        return CommuViewHolder(CommuRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: CommuViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            if (data.title.toString().length > 13) {
                commuTitle.text = data.title.toString().substring(0, 10) + "..."
            } else {
                commuTitle.text = data.title.toString()
            }
            if (data.content.toString().length > 100) {
                commuContents.text = data.content.toString().substring(0, 100) + "..."
            } else {
                commuContents.text = data.content.toString()
            }
            commuDate.text = data.date_time.toString()
        }

        holder.apply {
            bind(data)
        }
    }
}
