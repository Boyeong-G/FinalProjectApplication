package com.example.finalprojectapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectapplication.databinding.CommentsRecyclerviewBinding

class CommentsViewHolder(val binding: CommentsRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
class CommentsAdapter (val itemList: MutableList<CommentsData>): RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(CommentsRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            comment.text = data.comment.toString()
            commentEmail.text = data.email.toString()
            commentDate.text = data.date_time.toString()
        }
    }
}
