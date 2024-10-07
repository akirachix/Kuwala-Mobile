
package com.akirachix.dishhub

import Vegetables
import VegetablesAdapter
import com.akirachix.dishhub.databinding.ActivityVegetablesCategoryBinding
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class VegetablesCategory : AppCompatActivity() {

    private lateinit var binding: ActivityVegetablesCategoryBinding
    private lateinit var adapter: VegetablesAdapter
    private var foodItems: List<Vegetables> = listOf()
    private val selectedItems = mutableListOf<Vegetables>() // Store selected items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetablesCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupBackButton()
        fetchFoodItems()
        setupSaveButton() // Set up the save button
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
                saveSelectedItems()  // Call the function to save selected items
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

        // Notify adapter about the change
        adapter.notifyItemChanged(foodItems.indexOf(item))
    }

    private fun saveSelectedItems() {
        selectedItems.forEach { vegetable ->
            val pantryItem = PantryItems(
                name = vegetable.name,
                quantity = vegetable.quantity
            )
            PantryRepository.addPantryItem(pantryItem) // Method to add item to pantry

            Toast.makeText(this, "${pantryItem.name} saved to pantry", Toast.LENGTH_SHORT).show()
        }

        selectedItems.clear()
    }
}
