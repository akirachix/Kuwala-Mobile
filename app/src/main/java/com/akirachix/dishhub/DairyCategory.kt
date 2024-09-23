package com.akirachix.dishhub

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityDairyCategoryBinding

class DairyCategory : AppCompatActivity() {
    lateinit var binding: ActivityDairyCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDairyCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayDairy()

        val backArrow: ImageView = findViewById(R.id.imageView)
        backArrow.setOnClickListener {
            finish()
        }
    }

    fun displayDairy(){
        val dairy1 = Dairy("Fresh milk",2,"")
        val dairy3 = Dairy("Cabbage",3,"")
        val dairy4 = Dairy("Beef",8,"")
        val dairy5 = Dairy("Mutton",7,"")
        val dairy6 = Dairy("Pork",2,"")
        val dairy7 = Dairy("Cheese",5,"")
        val dairy8 = Dairy("Fresh milk",2,"")
        val dairy9 = Dairy("Cabbage",3,"")
        val dairy10 = Dairy("Beef",8,"")
        val dairy11 = Dairy("Mutton",7,"")
        val dairy12 = Dairy("Pork",2,"")
        val dairy13 = Dairy("Butter",5,"")

        val myDairy = listOf(dairy1,dairy3,dairy4,dairy5,dairy6,dairy7,
        dairy8,dairy9,dairy10,dairy11,dairy12,dairy13)

        val dairyAdapter = DairyAdapter(myDairy)
        binding.recyclerView.adapter= dairyAdapter

    }
}