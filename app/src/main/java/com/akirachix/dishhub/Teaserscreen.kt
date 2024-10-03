package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityTeaserscreenBinding

class Teaserscreen : AppCompatActivity() {

    private lateinit var binding: ActivityTeaserscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeaserscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            Log.d("Teaserscreen", "Already have an account? log in clicked")
            val loginIntent = Intent(this, Login::class.java)
            startActivity(loginIntent)
        }

        binding.btnbutton.setOnClickListener {
            Log.d("Teaserscreen", "Get Started button clicked")
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }
}
