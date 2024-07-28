package com.example.finalprojectapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectapplication.databinding.ActivityCommuBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.Query

class CommuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityCommuBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var headerView: View
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        toggle = ActionBarDrawerToggle(this, binding.commu, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
        binding.commuDrawerView.setNavigationItemSelectedListener(this)
        headerView = binding.commuDrawerView.getHeaderView(0)
        headerView.findViewById<Button>(R.id.login_button).setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            binding.commu.closeDrawers()
        }
        headerView.findViewById<Button>(R.id.logout_button).setOnClickListener {
            AuthApplication.auth.signOut()
            AuthApplication.email = null
            binding.commu.closeDrawers()
            Toast.makeText(baseContext,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()
            finish()
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val background = sharedPreferences.getString("background", "#E0FFDB")
        binding.commuDrawerView.setBackgroundColor(Color.parseColor(background))

        binding.commuSearchImgbotton.setOnClickListener {
            if (binding.commuSearchText.text.length > 0) {
                AuthApplication.db.collection("contents")
                    .orderBy("date_time", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener {result ->
                        val itemList = mutableListOf<CommuData>()
                        for (document in result) {
                            val item = document.toObject(CommuData::class.java)
                            item.docId = document.id
                            if (item.title!!.contains(binding.commuSearchText.text)) {
                                itemList.add(item)
                            }
                            else if (item.content!!.contains(binding.commuSearchText.text)) {
                                itemList.add(item)
                            }
                            binding.commuRecyclerView.adapter = CommuAdapter(itemList)
                            binding.commuRecyclerView.layoutManager = LinearLayoutManager(this)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_LONG).show()
                    }
            }

        }
        binding.commuAddButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        binding.commuImageButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                binding.commu.closeDrawers()
                true
            }
            R.id.item_written -> {
                if (AuthApplication.checkAuth()) {
                    Toast.makeText(applicationContext, "현재 페이지입니다.", Toast.LENGTH_LONG).show()
                    binding.commu.closeDrawers()
                }
                else {
                    Toast.makeText(applicationContext, "로그인 후 이용 가능합니다.", Toast.LENGTH_LONG).show()
                    binding.commu.closeDrawers()
                    finish()
                }
                true
            }
            R.id.item_endangered -> {
                val intent = Intent(this, EndangeredActivity::class.java)
                startActivity(intent)
                binding.commu.closeDrawers()
                true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_commu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        when(item.itemId) {
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
        binding.commuDrawerView.setBackgroundColor(Color.parseColor(background))
    }

    override fun onStart() {
        super.onStart()

        AuthApplication.db.collection("contents")
            .orderBy("date_time", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                val itemList = mutableListOf<CommuData>()
                for (document in result) {
                    val item = document.toObject(CommuData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                    binding.commuRecyclerView.adapter = CommuAdapter(itemList)
                    binding.commuRecyclerView.layoutManager = LinearLayoutManager(this)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_LONG).show()
            }

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