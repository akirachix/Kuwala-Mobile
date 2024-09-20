package com.akirachix.dishhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityTeaserscreenBinding

class Teaserscreen : AppCompatActivity() {
    lateinit var binding: ActivityTeaserscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTeaserscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}