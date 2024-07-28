package com.example.finalprojectapplication

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.finalprojectapplication.databinding.ActivityResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    lateinit var sharedPreferences: SharedPreferences
    var imageUrl1: String? = null
    var imageUrl2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val font = sharedPreferences.getString("font", "20.0f")
        binding.resultBiogyNmText.textSize = font!!.toFloat()
        binding.resultBiogyNm.textSize = font!!.toFloat()
        binding.resultLvbngKrlngNmText.textSize = font!!.toFloat()
        binding.resultLvbngKrlngNm.textSize = font!!.toFloat()
        binding.resultLvbngTpcdNmText.textSize = font!!.toFloat()
        binding.resultLvbngTpcdNm.textSize = font!!.toFloat()
        binding.resultFamlKorNmText.textSize = font!!.toFloat()
        binding.resultFamlKorNm.textSize = font!!.toFloat()
        binding.resultFamlNmText.textSize = font!!.toFloat()
        binding.resultFamlNm.textSize = font!!.toFloat()
        binding.resultOrdKrlngNmText.textSize = font!!.toFloat()
        binding.resultOrdKrlngNm.textSize = font!!.toFloat()
        binding.resultOrdNmText.textSize = font!!.toFloat()
        binding.resultOrdNm.textSize = font!!.toFloat()
        binding.resultLvbngDscrt.textSize = font!!.toFloat()
        val image = sharedPreferences.getString("image", "250")

        if (intent.getStringExtra("searchNum") != null) {
            val searchString = intent.getStringExtra("searchNum")
            val call: Call<ResultXmlResponse> = RetrofitConnectionResultOrganism.xmlNetworkServiceResultOrganism.getResultXmlList(
                "2U/7rh8BYy3rdfY9MMYshM2G6wxwWM7JCT7S+8eWqtfNFdOthVevI4J7gtZqdUCZ6KS0AJOu+eQ9dcDfxCNGVQ==",
                searchString!!.toInt()
            )
            call?.enqueue(object : Callback<ResultXmlResponse> {
                override fun onResponse(call: Call<ResultXmlResponse>, response: Response<ResultXmlResponse>) {
                    if(response.isSuccessful){
                        val model = response.body()!!.body!!.item
                        binding.resultBiogyNm.text = model.biogyNm.toString()
                        binding.resultChildLvbngPilbkNo.text = model.childLvbngPilbkNo.toString()
                        binding.resultCprtCtnt.text = model.cprtCtnt.toString()
                        binding.resultFamlKorNm.text = model.famlKrlngNm.toString()
                        binding.resultFamlNm.text = model.famlNm.toString()
                        binding.resultLvbngKrlngNm.text = model.lvbngKrlngNm.toString()
                        binding.resultLvbngTpcdNm.text = model.lvbngTpcdNm.toString()
                        binding.resultOrdKrlngNm.text = model.ordKrlngNm.toString()
                        binding.resultOrdNm.text = model.ordNm.toString()
                        imageUrl1 = model.imgUrl1.toString()
                        imageUrl2 = model.imgUrl2.toString()
                        if (image.equals("250")) {
                            binding.resultImgUrl1.visibility = View.VISIBLE
                            binding.resultImgUrlS.visibility = View.GONE
                            binding.resultImgUrlM.visibility = View.GONE
                            Glide.with(binding.root)
                                .load(imageUrl1)
                                .into(binding.resultImgUrl1)
                        }
                        else if (image.equals("170")) {
                            binding.resultImgUrl1.visibility = View.GONE
                            binding.resultImgUrlS.visibility = View.GONE
                            binding.resultImgUrlM.visibility = View.VISIBLE
                            Glide.with(binding.root)
                                .load(imageUrl1)
                                .into(binding.resultImgUrlM)
                        }
                        else {
                            binding.resultImgUrl1.visibility = View.GONE
                            binding.resultImgUrlS.visibility = View.VISIBLE
                            binding.resultImgUrlM.visibility = View.GONE
                            Glide.with(binding.root)
                                .load(imageUrl1)
                                .into(binding.resultImgUrlS)
                        }

                        if (imageUrl2!!.length > 4) {
                            Glide.with(binding.root)
                                .load(imageUrl2)
                                .override(97, 97)
                                .into(binding.resultImgUrl2)
                            binding.resultImgUrl2.visibility = View.VISIBLE

                            binding.resultImgUrl2.setOnClickListener {
                                val img = imageUrl1
                                imageUrl1 = imageUrl2
                                imageUrl2 = img

                                if (image.equals("250")) {
                                    binding.resultImgUrl1.visibility = View.VISIBLE
                                    binding.resultImgUrlS.visibility = View.GONE
                                    binding.resultImgUrlM.visibility = View.GONE
                                    Glide.with(binding.root)
                                        .load(imageUrl1)
                                        .into(binding.resultImgUrl1)
                                }
                                else if (image.equals("170")) {
                                    binding.resultImgUrl1.visibility = View.GONE
                                    binding.resultImgUrlS.visibility = View.GONE
                                    binding.resultImgUrlM.visibility = View.VISIBLE
                                    Glide.with(binding.root)
                                        .load(imageUrl1)
                                        .into(binding.resultImgUrl1)
                                }
                                else {
                                    binding.resultImgUrl1.visibility = View.GONE
                                    binding.resultImgUrlS.visibility = View.VISIBLE
                                    binding.resultImgUrlM.visibility = View.GONE
                                    Glide.with(binding.root)
                                        .load(imageUrl1)
                                        .into(binding.resultImgUrlS)
                                }
                                Glide.with(binding.root)
                                    .load(imageUrl2)
                                    .override(97, 97)
                                    .into(binding.resultImgUrl2)
                            }
                        }
                        if (model.biogyNm.toString().length > 25) {
                            binding.resultBiogyNm.textSize = 15.0f
                        }
                        if (model.hbttNm.toString().length > 0) {
                            binding.resultHbttNm.text = model.hbttNm.toString()
                            binding.resultHbttNmTitle.visibility = View.VISIBLE
                            binding.resultHbttNm.visibility = View.VISIBLE
                        }
                        if (model.prtctSpecsTpcdNm.toString().length > 0) {
                            binding.resultPrtctSpecsTpcdNm.text = model.prtctSpecsTpcdNm.toString()
                            binding.resultPrtctSpecsTpcdNmTitle.visibility = View.VISIBLE
                            binding.resultPrtctSpecsTpcdNm.visibility = View.VISIBLE
                        }

                        val regex = Regex("br/")
                        val lvbngDscrt = model.lvbngDscrt.toString()
                        val result = regex.replace(lvbngDscrt, "\n")
                        val regex2 = Regex("[A-Za-z/;&]")
                        val hanguel = regex2.replace(result, "")
                        binding.resultLvbngDscrt.text = hanguel
                    }
                }

                override fun onFailure(call: Call<ResultXmlResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "인터넷 연결 오류", Toast.LENGTH_LONG).show()
                    finish()
                }
            })
        } else {
            Toast.makeText(applicationContext, "인터넷 연결 오류", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}