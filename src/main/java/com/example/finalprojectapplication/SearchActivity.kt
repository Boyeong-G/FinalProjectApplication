package com.example.finalprojectapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectapplication.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var sharedPreferences: SharedPreferences
    var data: String? = null
    var count: String? = "false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        count = intent.getStringExtra("count")

        if (!(count.equals("false"))) {
            if (count!!.toInt() % 2 == 0) {
                if (AuthApplication.checkAuth() == true) {
                    val permissionLauncher = registerForActivityResult(
                        ActivityResultContracts.RequestMultiplePermissions() ) {
                        if (it.all { permission -> permission.value == true }) {
                            noti()
                        }
                        else {
                            Toast.makeText(this, "앱에 알림 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(this,"android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                            noti()
                        }
                        else {
                            permissionLauncher.launch( arrayOf( "android.permission.POST_NOTIFICATIONS"  ) )
                        }
                    }
                    else {
                        noti()
                    }
                }
            }
        }

        try {
            val fileR = File(filesDir, "searchString.txt")
            val readstream: BufferedReader = fileR.reader().buffered()
            data = readstream.readLine()
            binding.searchString.text = data!!.split(';')[0].toString() + '(' + data!!.split(';')[1].toString() + ')'
            binding.searchDate.text = data!!.split(';')[2].toString()

            if (data != null) {
                binding.searchString.visibility = View.VISIBLE
                binding.searchDate.visibility = View.VISIBLE
                var num = 2
                if (data!!.split(';')[1].equals("국명")) {
                    num = 1
                }
                val call: Call<XmlResponse> = RetrofitConnectionOrganism.xmlNetworkServiceOrganism.getXmlList(
                    "2U/7rh8BYy3rdfY9MMYshM2G6wxwWM7JCT7S+8eWqtfNFdOthVevI4J7gtZqdUCZ6KS0AJOu+eQ9dcDfxCNGVQ==",
                    num,
                    data!!.split(';')[0].toString(),
                    10,
                    1
                )
                call?.enqueue(object : Callback<XmlResponse> {
                    override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                        if(response.isSuccessful){
                            binding.searchRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                            binding.searchRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                        }
                    }

                    override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                        Log.d("mobileApp", "onFailure ${call.request()}")
                        Toast.makeText(applicationContext, "인터넷 연결 오류", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        catch (_: FileNotFoundException) {
        }
        catch (_: NullPointerException) {
        }

        binding.searchImgbotton.setOnClickListener {
            if (binding.searchText.textSize > 0) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val fileW = File(filesDir, "searchString.txt")
                val writestream: OutputStreamWriter = fileW.writer()
                if (binding.radioBiogyNm.isChecked) {
                    writestream.write(binding.searchText.text.toString() + ';' + binding.radioBiogyNm.text + ';' + dateFormat.format(System.currentTimeMillis()))
                }
                else {
                    writestream.write(binding.searchText.text.toString() + ";" + binding.radioFamilyNm.text + ';' + dateFormat.format(System.currentTimeMillis()))
                }
                writestream.flush()

                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("searchString", binding.searchText.text.toString())
                if (binding.radioFamilyNm.isChecked) {
                    intent.putExtra("radioType", binding.radioFamilyNm.text.toString())
                } else {
                    intent.putExtra("radioType", binding.radioBiogyNm.text.toString())
                }

                if (count.equals("false")) {
                    intent.putExtra("count", count!!.toString())
                }
                else {
                    intent.putExtra("count", (count!!.toInt() + 1).toString())
                }
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "검색어를 입력하세요.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun noti() {
        val intent = Intent(this, CommuActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        } // 알림 클릭 시 이동 추가 구현

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE) // 알림 클릭 시 이동 추가 구현

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId="one-channel"
            val channelName="My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.drawable.alarm_icon)
            setWhen(System.currentTimeMillis())
            setContentTitle("원하시는 검색 결과가 없나요?")
            setContentText("그렇다면 Q&A에 질문해 보세요. 알림창 터치 시 Q&A 게시판으로 이동합니다.")

            setContentIntent(pendingIntent) // 알림 클릭 시 이동 추가 구현
            setAutoCancel(true) // 알림 클릭 시 이동 추가 구현
        }

        manager.notify(11, builder.build())
    }
}