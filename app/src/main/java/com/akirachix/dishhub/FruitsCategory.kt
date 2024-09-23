package com.akirachix.dishhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityFruitsCategoryBinding

class FruitsCategory : AppCompatActivity() {
    lateinit var binding: ActivityFruitsCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayFruits()
    }

    fun displayFruits(){
        val fruit1 = Fruits("Apple",2,"")
        val fruit2 = Fruits("Mango",4,"")
        val fruit3 = Fruits("Banana",3,"")
        val fruit4 = Fruits("Orange",8,"")
        val fruit5 = Fruits("Guava",7,"")
        val fruit6 = Fruits("Avocado",2,"")
        val fruit7 = Fruits("Watermelon",5,"")
        val fruit8 = Fruits("Pineapple",3,"")
        val fruit9 = Fruits("Grapes",4,"")

        val myFruits = listOf(fruit1,fruit2,fruit3,fruit4,fruit5,fruit6,fruit7,fruit8,fruit9)

        val FruitsAdapter = FruitsAdapter(myFruits)
        binding.recyclerView.adapter= FruitsAdapter

    }
}