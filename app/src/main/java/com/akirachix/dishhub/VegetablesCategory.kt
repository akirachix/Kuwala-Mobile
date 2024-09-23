package com.akirachix.dishhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityVegetablesCategoryBinding

class VegetablesCategory : AppCompatActivity() {
    lateinit var binding: ActivityVegetablesCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetablesCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recycler.layoutManager = LinearLayoutManager(this)
        displayVegetables()
    }

    fun displayVegetables(){
        val vegetable1 = Vegetables("Kales",2,"")
        val vegetable2 = Vegetables("spinach",4,"")
        val vegetable3 = Vegetables("Cabbage",3,"")
        val vegetable4 = Vegetables("Tomatoes",8,"")
        val vegetable5 = Vegetables("Potatoes",1,"")
        val vegetable6 = Vegetables("Corriandar",2,"")
        val vegetable7 = Vegetables("Brocolli",5,"")
        val vegetable8 = Vegetables("Pumpkin",3,"")

        val myVegetables = listOf(vegetable1,vegetable2,vegetable3,vegetable4,vegetable5,vegetable6,vegetable7,vegetable8)

        val vegetablesAdapter = VegetablesAdapter(myVegetables)
        binding.recycler.adapter= vegetablesAdapter

    }
}