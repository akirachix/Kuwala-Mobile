
package com.akirachix.dishhub

import DairyAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityDairyCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DairyCategory : AppCompatActivity() {

    private lateinit var binding: ActivityDairyCategoryBinding
    private lateinit var adapter: DairyAdapter
    private var foodItems: List<Dairy> = listOf()
    private val selectedItems = mutableListOf<Dairy>() // Store selected items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDairyCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupBackButton()
        fetchFoodItems()  // Fetch Dairy items
        setupSaveButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DairyAdapter(emptyList()) { item -> onFoodItemSelected(item) }
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
        binding.button.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                saveSelectedItems()
            } else {
                Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fetch food items from the Dairy category
    private fun fetchFoodItems() {
        val retrofit = DairyRetrofitInstance.api

        retrofit.getDairyItems().enqueue(object : Callback<List<Dairy>> {
            override fun onResponse(call: Call<List<Dairy>>, response: Response<List<Dairy>>) {
                if (response.isSuccessful) {
                    foodItems = response.body() ?: emptyList()
                    adapter.updateItems(foodItems)
                } else {
                    Toast.makeText(this@DairyCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Dairy>>, t: Throwable) {
                Toast.makeText(this@DairyCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
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

    private fun onFoodItemSelected(item: Dairy) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            item.isSelected = false
            Toast.makeText(this, "${item.name} deselected", Toast.LENGTH_SHORT).show()
        } else {
            selectedItems.add(item)
            item.isSelected = true
            Toast.makeText(this, "${item.name} selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveSelectedItems() {
        selectedItems.forEach { dairy ->
            val pantryItem = PantryItems(
                name = dairy.name,
                quantity = dairy.quantity.toString(),
                quantity1 = dairy.quantity,
                s = "", // Adjust as necessary
                s1 = "" // Adjust as necessary
            )
            PantryRepository.addPantryItem(pantryItem)

            Toast.makeText(this, "${pantryItem.name} saved to pantry", Toast.LENGTH_SHORT).show()
        }

        selectedItems.clear()
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<Dairy>>) {
    // Custom enqueue extension if needed
}

