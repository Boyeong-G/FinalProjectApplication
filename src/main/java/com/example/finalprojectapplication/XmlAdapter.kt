package com.example.finalprojectapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ContentInfoCompat
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectapplication.databinding.EndangeredRecyclerviewBinding
import com.example.finalprojectapplication.databinding.ItemRecyclerviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class XmlViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: orgaXmlItem) {
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, ResultActivity::class.java)
            intent.putExtra("searchNum", data.childLvbngPilbkNo.toString())
            intent.run { binding.root.context.startActivity(this) }
        }
    }
}
class XmlAdapter(val datas: MutableList<orgaXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]

        val biogyNm = model.biogyNm.toString().substring(0, 15) + "..."
        binding.itemBiogyNm.text = biogyNm
        binding.itemFamilyKorNm.text = model.familyKorNm.toString()
        binding.itemFamilyNm.text = model.familyNm.toString()
        binding.itemLvbngKrlngNm.text = model.lvbngKrlngNm.toString()
        binding.itemLvbngTpcdNm.text = model.lvbngTpcdNm.toString()
        binding.itemCprtCtnt.text = model.cprtCtnt.toString()

        holder.apply {
            bind(model)
        }
    }
}

class EndangeredViewHolder(val binding: EndangeredRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)  {
//    fun bind2(data: orgaResultXmlItem) {
//        binding.root.setOnClickListener {
//            val intent = Intent(binding.root.context, ResultActivity::class.java)
//            intent.putExtra("searchNum", data.childLvbngPilbkNo.toString())
//            intent.run { binding.root.context.startActivity(this) }
//        }
//    }
}
class EndangeredAdapter(val datas: MutableList<orgaResultXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EndangeredViewHolder(EndangeredRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as EndangeredViewHolder).binding
        val model = datas!![position]

        binding.endangeredChildLvbngPilbkNo.text = "[" + model.childLvbngPilbkNo.toString() + "]"
        binding.endangeredLvbngKrlngNm.text = model.lvbngKrlngNm.toString()
        binding.endangeredLvbngTpcdNm.text = model.lvbngTpcdNm.toString()
        binding.endangeredCprtCtnt.text = model.cprtCtnt.toString()
        binding.endangeredLvbngTpcdNm.text = model.lvbngTpcdNm.toString()
        binding.endangeredPrtctSpecsTpcdNm.text = model.prtctSpecsTpcdNm.toString()
        Glide.with(binding.root)
            .load(model.imgUrl1)
            .into(binding.endangeredImgUrl1)

//        holder.apply {
//            bind2(model)
//        }
    }
}