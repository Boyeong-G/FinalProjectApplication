package com.example.finalprojectapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectapplication.databinding.ActivityListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val count = intent.getStringExtra("count")

        var searchString = "검은"
        var type = 1

        if (intent.getStringExtra("searchString") != null) {
            searchString = intent.getStringExtra("searchString").toString()
            var radioType = intent.getStringExtra("radioType").toString()
            binding.resultSearchText.hint = searchString + "(" + radioType + ") 검색 결과"
        }

        val call: Call<XmlResponse> = RetrofitConnectionOrganism.xmlNetworkServiceOrganism.getXmlList(
            "2U/7rh8BYy3rdfY9MMYshM2G6wxwWM7JCT7S+8eWqtfNFdOthVevI4J7gtZqdUCZ6KS0AJOu+eQ9dcDfxCNGVQ==",
            type,
            searchString,
            50,
            1
        )
        call?.enqueue(object : Callback<XmlResponse> {
            override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                if(response.isSuccessful){
                    binding.listRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                    binding.listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    binding.listRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                Log.d("mobileApp", "onFailure ${call.request()}")
                Toast.makeText(applicationContext, "인터넷 연결 오류", Toast.LENGTH_LONG).show()
            }
        })

        binding.resultSearchText.isFocusable = false
        binding.resultSearchText.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("count", count.toString())
            startActivity(intent)
        }

        binding.resultSearchImgbotton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("count", count.toString())
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}