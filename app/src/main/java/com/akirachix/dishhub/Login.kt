package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.dishhub.databinding.ActivityLoginBinding
import com.akirachix.dishhub.databinding.ActivitySignupBinding

class Login : AppCompatActivity() {
lateinit var binding: ActivityLoginBinding
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.txtsignUp.setOnClickListener {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
    binding.btnlogin.setOnClickListener {
        val intent = Intent(this, Categories::class.java)
        startActivity(intent)
    }
}
}




