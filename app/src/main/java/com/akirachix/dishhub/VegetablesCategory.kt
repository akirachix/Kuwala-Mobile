package com.akirachix.dishhub

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityVegetablesCategoryBinding

class VegetablesCategory : AppCompatActivity() {
    private lateinit var binding: ActivityVegetablesCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetablesCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        displayVegetables()

        val backArrow: ImageView = findViewById(R.id.imageView)
        backArrow.setOnClickListener {
            finish()
        }
    }

    private fun displayVegetables() {
        val vegetable1 = Vegetables("Kales", 2, "")
        val vegetable2 = Vegetables("Spinach", 4, "")
        val vegetable3 = Vegetables("Cabbage", 3, "")
        val vegetable4 = Vegetables("Tomatoes", 8, "")
        val vegetable5 = Vegetables("Potatoes", 1, "")
        val vegetable6 = Vegetables("Coriander", 2, "")
        val vegetable7 = Vegetables("Broccoli", 5, "")
        val vegetable8 = Vegetables("Pumpkin", 3, "")
        val vegetable9 = Vegetables("Spinach",2,"")
        val vegetable10 = Vegetables("Kales",6,"")
        val vegetable11 = Vegetables("Tomatoes", 8, "")
        val vegetable12 = Vegetables("Potatoes", 1, "")
        val vegetable13 = Vegetables("Coriander", 2, "")


        val myVegetables = listOf(
            vegetable1, vegetable2, vegetable3,
            vegetable4, vegetable5, vegetable6,
            vegetable7, vegetable8, vegetable9, vegetable10,
            vegetable11, vegetable12, vegetable13
        )

        val vegetablesAdapter = VegetablesAdapter(myVegetables)
        binding.recycler.adapter = vegetablesAdapter
    }
}