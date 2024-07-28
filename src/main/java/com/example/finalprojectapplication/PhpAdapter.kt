package com.example.finalprojectapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectapplication.databinding.EndangeredRecyclerviewBinding

class PhpViewHolder(val binding: EndangeredRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class PhpAdapter (val context: Context, val itemList: ArrayList<EndangeredData>): RecyclerView.Adapter<PhpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhpViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhpViewHolder(EndangeredRecyclerviewBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: PhpViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            endangeredPrtctSpecsTpcdNm.text = data.level.toString()
            endangeredChildLvbngPilbkNo.text = data.num.toString()
            endangeredLvbngKrlngNm.text = data.name.toString()
            endangeredLvbngTpcdNm.text = data.spe.toString()

            Glide.with(root)
                .load(data.img.toString())
                .into(endangeredImgUrl1)

            root.setOnClickListener {
                val intent = Intent(context, ResultActivity::class.java).apply {
                    putExtra("searchNum", endangeredChildLvbngPilbkNo.text)
                }.run {
                    context.startActivity(this)
                }
            }
        }
    }
}