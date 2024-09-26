package com.akirachix.dishhub.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.Categories

import com.akirachix.dishhub.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtlogin.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, Categories::class.java)
            startActivity(intent)
        }
    }
}


