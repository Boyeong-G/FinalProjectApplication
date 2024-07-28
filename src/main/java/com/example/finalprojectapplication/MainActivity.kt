package com.example.finalprojectapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding:ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var headerView: View
    lateinit var sharedPreferences: SharedPreferences
    var count = "false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        toggle = ActionBarDrawerToggle(this, binding.main, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
        binding.mainDrawerView.setNavigationItemSelectedListener(this)
        headerView = binding.mainDrawerView.getHeaderView(0)
        headerView.findViewById<Button>(R.id.login_button).setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            binding.main.closeDrawers()
        }
        headerView.findViewById<Button>(R.id.logout_button).setOnClickListener {
            AuthApplication.auth.signOut()
            AuthApplication.email = null
            binding.main.closeDrawers()
            Toast.makeText(baseContext,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()
            onStart()
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val background = sharedPreferences.getString("background", "#E0FFDB")
        binding.mainDrawerView.setBackgroundColor(Color.parseColor(background))
        val alarm = sharedPreferences.getBoolean("alarm", false)
        if (alarm == true) {
            count = "1"
        }
        else {
            count = "false"
        }

        val keyWords = arrayListOf("가", "벌", "이", "꽃", "달", "하", "무", "새", "풀")
        val dateFormat = SimpleDateFormat("dd")
        val today = dateFormat.format(System.currentTimeMillis()).toInt() % keyWords.size
        var searchString = keyWords[today]

        val call: Call<XmlResponse> = RetrofitConnectionOrganism.xmlNetworkServiceOrganism.getXmlList(
            "2U/7rh8BYy3rdfY9MMYshM2G6wxwWM7JCT7S+8eWqtfNFdOthVevI4J7gtZqdUCZ6KS0AJOu+eQ9dcDfxCNGVQ==",
            1,
            searchString,
            30,
            1
        )
        call?.enqueue(object : Callback<XmlResponse> {
            override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                if(response.isSuccessful){
                    binding.mainRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                    binding.mainRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    binding.mainRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                Log.d("mobileApp", "onFailure ${call.request()}")
                Toast.makeText(applicationContext, "인터넷 연결 오류", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_home -> {
                Toast.makeText(applicationContext, "현재 페이지입니다.", Toast.LENGTH_LONG).show()
                binding.main.closeDrawers()
                true
            }
            R.id.item_written -> {
                if (AuthApplication.checkAuth()) {
                    val intent = Intent(this, CommuActivity::class.java)
                    startActivity(intent)
                    binding.main.closeDrawers()
                }
                else {
                    Toast.makeText(applicationContext, "로그인 후 이용 가능합니다.", Toast.LENGTH_LONG).show()
                    binding.main.closeDrawers()
                }
                true
            }
            R.id.item_endangered -> {
                val intent = Intent(this, EndangeredActivity::class.java)
                startActivity(intent)
                binding.main.closeDrawers()
                true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        when(item.itemId) {
            R.id.menu_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("count", count.toString())
                startActivity(intent)
                true
            }
            R.id.menu_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val background = sharedPreferences.getString("background", "#E0FFDB")
        binding.mainDrawerView.setBackgroundColor(Color.parseColor(background))

        val alarm = sharedPreferences.getBoolean("alarm", false)
        if (alarm == true) {
            count = "1"
        }
        else {
            count = "false"
        }
    }

    override fun onStart() {
        super.onStart()

        val login_button = headerView.findViewById<Button>(R.id.login_button)
        val login_imageButton = headerView.findViewById<ImageButton>(R.id.login_imageButton)
        val logout_button = headerView.findViewById<Button>(R.id.logout_button)
        val logout_imageButton_ = headerView.findViewById<ImageButton>(R.id.logout_imageButton)
        val text = headerView.findViewById<TextView>(R.id.login_user_email)
        val logout_text = headerView.findViewById<TextView>(R.id.logout_textView)
        val login_text = headerView.findViewById<TextView>(R.id.login_textView)
        val image = headerView.findViewById<ImageView>(R.id.imageView)

        if(AuthApplication.checkAuth()) {
            login_button.visibility = View.GONE
            login_imageButton.visibility = View.GONE
            logout_button.visibility = View.VISIBLE
            logout_imageButton_.visibility = View.VISIBLE
            text.text =  "${AuthApplication.email}"
            text.visibility = View.VISIBLE
            login_text.visibility = View.VISIBLE
            image.visibility = View.VISIBLE
            logout_text.visibility = View.GONE
        } else {
            login_button.visibility = View.VISIBLE
            login_imageButton.visibility = View.VISIBLE
            logout_button.visibility = View.GONE
            logout_imageButton_.visibility = View.GONE
            text.visibility = View.GONE
            login_text.visibility = View.GONE
            image.visibility = View.GONE
            logout_text.visibility = View.VISIBLE
        }
    }
}