package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.dishhub.databinding.ActivityTeaserscreenBinding

class Teaserscreen : AppCompatActivity() {
    lateinit var binding: ActivityTeaserscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTeaserscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}