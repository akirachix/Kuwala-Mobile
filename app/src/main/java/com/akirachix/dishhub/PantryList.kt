package com.akirachix.dishhub

import PantryAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.PantryListBinding

class PantryList : AppCompatActivity() {
    private lateinit var binding: PantryListBinding
    private lateinit var pantryAdapter: PantryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PantryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        displayItems()
    }
    private fun setupRecyclerView(){
        binding.rvpantry.layoutManager = LinearLayoutManager(this)
        pantryAdapter = PantryAdapter(mutableListOf())
        binding.rvpantry.adapter = pantryAdapter
    }
    private fun displayItems() {
        binding.rvpantry.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            PantryItems("", "Onion", 4 ),
            PantryItems("", "Tomatoes", 5),
            PantryItems("", "Rice", 3),
            PantryItems("", "Milk", 2),
            PantryItems("", "Beef", 4),
            PantryItems("", "Kales", 1),
            PantryItems("", "Pork", 3)
        )

        val pantryAdapter = PantryAdapter(items)
        binding.rvpantry.adapter = pantryAdapter
    }
}