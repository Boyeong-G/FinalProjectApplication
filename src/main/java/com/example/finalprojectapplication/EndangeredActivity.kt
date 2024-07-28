package com.example.finalprojectapplication

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectapplication.databinding.ActivityEndangeredBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EndangeredActivity : AppCompatActivity() {
    lateinit var binding: ActivityEndangeredBinding
    lateinit var sharedPreferences: SharedPreferences

//    1~2498 까지는 식물 / 2499~3431 까지는 곤충 / 3432~4073 까지는 버섯 / 4074~4347 까지는 야생 동물 3429
// 359, 591, 932, 1795, 2284, 21, 86, 89, 121, 373, 446, 555, 578, 584, 620, 653, 665, 669, 733, 763, 772, 775, 972, 1040, 1202, 1208, 1281, 1314, 1429, 1456, 1482, 1500, 1534, 1560, 1572, 1615, 1625, 1650, 1723, 1830, 1894, 1964, 2035, 2102, 2141, 2153, 2158, 2160, 2177, 2286, 2315, 2442, 2457, 2950, 2968, 3003, 3186, 2469, 2682, 2794, 3014, 3124, 4010, 4082, 4095, 4120, 4132, 4238, 4267, 4290, 4301, 4325, 4326, 4328, 4329, 4330, 4335, 4337, 4088, 4088, 4091, 4103, 4105, 4127, 4135, 4139, 4142, 4155, 4172, 4176, 4184, 4186, 4187, 4188, 4208, 4216, 4222, 4227, 4237, 4245, 4255, 4262, 4268, 4269, 4270, 4271, 4272, 4280, 4297, 4299, 4307, 4319, 4324, 4327, 4336

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEndangeredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.horizontalScrollview.isHorizontalScrollBarEnabled = false

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val background = sharedPreferences.getString("background", "#E0FFDB")
        if (background.equals("#E0FFDB")) {
            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttonchecked)
        }
        else if (background.equals("#E6FFFF")) {
            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttoncheckedblue)
        }
        else if (background.equals("#FFFED7")) {
            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttoncheckedyellow)
        }
        else {
            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttoncheckedpink)
        }

        var prtctSpecsTpcdNm = ""
        val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(prtctSpecsTpcdNm)

        call?.enqueue(object: Callback<PhpResponse> {
            override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                if(response.isSuccessful) {
                    binding.endangeredRecyclerView.adapter = PhpAdapter(this@EndangeredActivity, response.body()?.result!!)
                    binding.endangeredRecyclerView.layoutManager = LinearLayoutManager(this@EndangeredActivity)
                    binding.endangeredRecyclerView.addItemDecoration(DividerItemDecoration(this@EndangeredActivity, LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "송신 오류", Toast.LENGTH_LONG).show()
            }
        })

        binding.endangeredButtonAll.setOnClickListener {
            if (background.equals("#E0FFDB")) {
                binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttonchecked)
            }
            else if (background.equals("#E6FFFF")) {
                binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttoncheckedblue)
            }
            else if (background.equals("#FFFED7")) {
                binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttoncheckedyellow)
            }
            else {
                binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbuttoncheckedpink)
            }

            binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbutton)

            var prtctSpecsTpcdNm = ""
            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(prtctSpecsTpcdNm)

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful) {
                        binding.endangeredRecyclerView.adapter = PhpAdapter(this@EndangeredActivity, response.body()?.result!!)
                        binding.endangeredRecyclerView.layoutManager = LinearLayoutManager(this@EndangeredActivity)
                        binding.endangeredRecyclerView.addItemDecoration(DividerItemDecoration(this@EndangeredActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "송신 오류", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.endangeredButtonAnimal.setOnClickListener {
            if (background.equals("#E0FFDB")) {
                binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbuttonchecked)
            }
            else if (background.equals("#E6FFFF")) {
                binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbuttoncheckedblue)
            }
            else if (background.equals("#FFFED7")) {
                binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbuttoncheckedyellow)
            }
            else {
                binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbuttoncheckedpink)
            }

            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbutton)

            var prtctSpecsTpcdNm = "animal"
            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(prtctSpecsTpcdNm)

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful) {
                        binding.endangeredRecyclerView.adapter = PhpAdapter(this@EndangeredActivity, response.body()?.result!!)
                        binding.endangeredRecyclerView.layoutManager = LinearLayoutManager(this@EndangeredActivity)
                        binding.endangeredRecyclerView.addItemDecoration(DividerItemDecoration(this@EndangeredActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "송신 오류", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.endangeredButtonPlant.setOnClickListener {
            if (background.equals("#E0FFDB")) {
                binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbuttonchecked)
            }
            else if (background.equals("#E6FFFF")) {
                binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbuttoncheckedblue)
            }
            else if (background.equals("#FFFED7")) {
                binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbuttoncheckedyellow)
            }
            else {
                binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbuttoncheckedpink)
            }

            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbutton)

            var prtctSpecsTpcdNm = "plant"
            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(prtctSpecsTpcdNm)

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful) {
                        binding.endangeredRecyclerView.adapter = PhpAdapter(this@EndangeredActivity, response.body()?.result!!)
                        binding.endangeredRecyclerView.layoutManager = LinearLayoutManager(this@EndangeredActivity)
                        binding.endangeredRecyclerView.addItemDecoration(DividerItemDecoration(this@EndangeredActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "송신 오류", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.endangeredButtonInsect.setOnClickListener {
            if (background.equals("#E0FFDB")) {
                binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbuttonchecked)
            }
            else if (background.equals("#E6FFFF")) {
                binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbuttoncheckedblue)
            }
            else if (background.equals("#FFFED7")) {
                binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbuttoncheckedyellow)
            }
            else {
                binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbuttoncheckedpink)
            }

            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbutton)

            var prtctSpecsTpcdNm = "insect"
            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(prtctSpecsTpcdNm)

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful) {
                        binding.endangeredRecyclerView.adapter = PhpAdapter(this@EndangeredActivity, response.body()?.result!!)
                        binding.endangeredRecyclerView.layoutManager = LinearLayoutManager(this@EndangeredActivity)
                        binding.endangeredRecyclerView.addItemDecoration(DividerItemDecoration(this@EndangeredActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "송신 오류", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.endangeredButtonMushroom.setOnClickListener {
            if (background.equals("#E0FFDB")) {
                binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbuttonchecked)
            }
            else if (background.equals("#E6FFFF")) {
                binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbuttoncheckedblue)
            }
            else if (background.equals("#FFFED7")) {
                binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbuttoncheckedyellow)
            }
            else {
                binding.endangeredButtonMushroom.setBackgroundResource(R.drawable.endangeredbuttoncheckedpink)
            }

            binding.endangeredButtonAll.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonPlant.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonInsect.setBackgroundResource(R.drawable.endangeredbutton)
            binding.endangeredButtonAnimal.setBackgroundResource(R.drawable.endangeredbutton)

            var prtctSpecsTpcdNm = "mushroom"
            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(prtctSpecsTpcdNm)

            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful) {
                        binding.endangeredRecyclerView.adapter = PhpAdapter(this@EndangeredActivity, response.body()?.result!!)
                        binding.endangeredRecyclerView.layoutManager = LinearLayoutManager(this@EndangeredActivity)
                        binding.endangeredRecyclerView.addItemDecoration(DividerItemDecoration(this@EndangeredActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "송신 오류", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}