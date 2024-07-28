package com.example.finalprojectapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalprojectapplication.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.signupButton.setOnClickListener {    // 가입 Button
            val email = binding.emailUp.text.toString()
            val password = binding.passwordUp.text.toString()
            if (!(password.length > 5)) {
                Toast.makeText(applicationContext,"[회원가입 실패] 비밀번호 길이가 너무 짧습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                AuthApplication.auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){task ->
                        binding.emailUp.text.clear()
                        binding.passwordUp.text.clear()
                        if(task.isSuccessful){
                            AuthApplication.auth.currentUser?.sendEmailVerification() // 이메일로 2차 인증
                                ?.addOnCompleteListener{sendTask ->
                                    if(sendTask.isSuccessful){
                                        Toast.makeText(baseContext,"[회원가입 성공] 메일을 확인해주세요", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(baseContext,"[메일발송 실패]", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                        else{
                            Toast.makeText(baseContext,"[회원가입 실패]", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)

        finish()
        return true
    }
}