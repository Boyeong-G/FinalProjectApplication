package com.example.finalprojectapplication

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.finalprojectapplication.databinding.ActivityCommuBinding
import com.example.finalprojectapplication.databinding.ActivityQresultBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import java.text.SimpleDateFormat

class QresultActivity : AppCompatActivity() {
    lateinit var binding: ActivityQresultBinding
    lateinit var sharedPreferences: SharedPreferences
    var docId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQresultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val font = sharedPreferences.getString("font", "20.0f")
        binding.qresultContent.textSize = font!!.toFloat()

        docId = intent.getStringExtra("docId")
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val date = intent.getStringExtra("date")
        val email = intent.getStringExtra("email")

        if (title!!.toString().length > 13) {
            binding.qresultTitle.textSize = 18.0f
        }
        binding.qresultTitle.text = title!!.toString()
        binding.qresultContent.text = content!!.toString()
        binding.qresultDate.text = date!!.toString()
        binding.qresultEmail.text = email!!.toString()
        val imageRef = AuthApplication.storage.reference.child("images/${docId}.jpg")
        imageRef.downloadUrl.addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Glide.with(binding.root)
                    .load(task.result)
                    .into(binding.qresultImgUrl1)
                binding.qresultImgUrl1.visibility = View.VISIBLE
            }
        }

        if (AuthApplication.email!!.equals(email)) {
            binding.qresultDelete.visibility = View.VISIBLE
        }
        else {
            binding.qresultDelete.visibility = View.GONE
        }

        val eventHandler = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    AuthApplication.db.collection("comments")
                        .orderBy("date_time", Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener {result ->
                            for (document in result) {
                                val item = document.toObject(CommentsData::class.java)
                                item.docId = document.id
                                if (item.parentId == docId!!) {
                                    AuthApplication.db.collection("comments").document(item.docId.toString()).delete()
                                }
                            }

                            AuthApplication.storage.reference.child("images/${docId}.jpg").delete()
                            AuthApplication.db.collection("contents").document(docId.toString()).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(applicationContext, "데이터 삭제 성공", Toast.LENGTH_LONG).show()
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(applicationContext, "데이터 삭제 실패", Toast.LENGTH_LONG).show()
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, "데이터 삭제 실패", Toast.LENGTH_LONG).show()
                        }
                }
                else {
                    Toast.makeText(applicationContext, "삭제 취소", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.qresultDelete.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("질문 삭제")
                setIcon(android.R.drawable.ic_dialog_alert)
                setMessage("작성한 질문을 삭제하시겠습니까?")
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                show()
            }
        }

        binding.qresultBotton.setOnClickListener {
            if (binding.qresultText.text.isNotEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val data = mapOf(
                    "email" to AuthApplication.email,
                    "comment" to binding.qresultText.text.toString(),
                    "date_time" to dateFormat.format(System.currentTimeMillis()),
                    "parentId" to docId.toString()
                )
                AuthApplication.db.collection("comments")
                    .add(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "데이터 저장 성공", Toast.LENGTH_LONG).show()
                        onStart()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "데이터 저장 실패", Toast.LENGTH_LONG).show()
                    }
            }
            binding.qresultText.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()

        AuthApplication.db.collection("comments")
            .orderBy("date_time", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {result ->
                val itemList = mutableListOf<CommentsData>()
                for (document in result) {
                    val item = document.toObject(CommentsData::class.java)
                    item.docId = document.id
                    if (item.parentId == docId!!) {
                        itemList.add(item)
                    }
                    binding.qresultRecyclerView.adapter = CommentsAdapter(itemList)
                    binding.qresultRecyclerView.layoutManager = LinearLayoutManager(this)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_LONG).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}