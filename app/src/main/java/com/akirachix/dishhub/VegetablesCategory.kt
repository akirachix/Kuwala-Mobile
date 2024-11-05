package com.akirachix.dishhub

import Vegetables
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityVegetablesCategoryBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory



class VegetablesCategory : AppCompatActivity() {
    private lateinit var binding: ActivityVegetablesCategoryBinding
    private lateinit var adapter: VegetablesAdapter
    private var foodItems: List<Vegetables> = listOf()
    private val selectedItems = mutableListOf<Vegetables>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetablesCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupBackButton()
        fetchFoodItems()
        setupSaveButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VegetablesAdapter(emptyList(), ::onFoodItemSelected)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupBackButton() {
        binding.imageView.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                saveSelectedItems()
            } else {
                Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchFoodItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dishhub-2ea9d6ca8e11.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        service.getFruits().enqueue(object : Callback<List<Vegetables>> {
            override fun onResponse(call: Call<List<Vegetables>>, response: Response<List<Vegetables>>) {
                if (response.isSuccessful) {
                    foodItems = response.body() ?: emptyList()
                    adapter.updateItems(foodItems)
                } else {
                    Toast.makeText(this@VegetablesCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Vegetables>>, t: Throwable) {
                Toast.makeText(this@VegetablesCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterItems(query: String) {
        val filteredList = foodItems.filter { it.name.contains(query, true) }
        if (filteredList.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            adapter.updateItems(filteredList)
        } else {
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun onFoodItemSelected(item: Vegetables) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            item.isSelected = false
            Toast.makeText(this, "${item.name} deselected", Toast.LENGTH_SHORT).show()
        } else {
            selectedItems.add(item)
            item.isSelected = true
            Toast.makeText(this, "${item.name} selected", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.post {
            adapter.notifyDataSetChanged()
        }
    }

    private fun saveSelectedItems() {
        val sharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val currentItems = sharedPreferences.getString("PantryItems", "") ?: ""
        val newVegetables = selectedItems.joinToString("|") { "${it.name},${it.quantity}" }
        val updatedItems = if (currentItems.isNotEmpty()) "$currentItems|$newVegetables" else newVegetables

        editor.putString("PantryItems", updatedItems)
        editor.apply()

        Toast.makeText(this, "Items saved to pantry", Toast.LENGTH_SHORT).show()

        selectedItems.clear()
        navigateAfterSave()  // Navigate to pantry after saving
    }

    private fun navigateAfterSave() {
        val intent = Intent(this, Categories::class.java)
        intent.putExtra("showFragment", "pantry") // optionally open pantry
        startActivity(intent)
        finish()
    }
}