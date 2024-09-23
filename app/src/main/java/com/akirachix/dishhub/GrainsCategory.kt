package com.akirachix.dishhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityGrainsCategoryBinding

class GrainsCategory : AppCompatActivity() {
    private lateinit var binding: ActivityGrainsCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrainsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayGrains()

        val backArrow: ImageView = findViewById(R.id.imageView)
        backArrow.setOnClickListener {
            finish()
        }
    }

    private fun displayGrains() {
        val grain1 = Grains("Green grams", 2, "")
        val grain2 = Grains("Beans", 4, "")
        val grain3 = Grains("Peas", 3, "")
        val grain4 = Grains("Millet", 8, "")
        val grain5 = Grains("Rice", 7, "")
        val grain6 = Grains("Brown rice", 2, "")
        val grain7 = Grains("Groundnuts", 5, "")
        val grain8 = Grains("Corn", 3, "")
        val grain9 = Grains("Yellow beans", 4, "")
        val grain10 = Grains("Sorghum", 3, "")
        val grain11 = Grains("Groundnuts", 5, "")
        val grain12 = Grains("Corn", 3, "")
        val grain13 = Grains("Yellow beans", 4, "")
        val grain14 = Grains("Sorghum", 3, "")

        val myGrains = listOf(grain1, grain2, grain3, grain4, grain5, grain6, grain7, grain8, grain9, grain10
        , grain11, grain12, grain13, grain14)

        val grainsAdapter = GrainsAdapter(myGrains)
        binding.recyclerView.adapter = grainsAdapter
    }
}