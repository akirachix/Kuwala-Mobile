



package com.akirachix.dishhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityGrainsCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GrainsCategory : AppCompatActivity() {
    private lateinit var binding: ActivityGrainsCategoryBinding
    private lateinit var adapter: GrainsAdapter
    private var foodItems: List<Grains> = listOf() // List of all food items
    private val selectedItems = mutableListOf<Grains>() // List of selected items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGrainsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupBackButton()
        fetchFoodItems()
        setupSaveButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GrainsAdapter(emptyList()) { onFoodItemSelected(it) }
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
        binding.imageView.setOnClickListener { onBackPressed() }
    }

    private fun setupSaveButton() {
        binding.button.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                saveSelectedItems()  // Call save function
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

        val service = retrofit.create(GrainsApiService::class.java)
        service.getFoodItems().enqueue(object : Callback<List<Grains>> {
            override fun onResponse(call: Call<List<Grains>>, response: Response<List<Grains>>) {
                if (response.isSuccessful) {
                    foodItems = response.body() ?: emptyList()
                    adapter.updateItems(foodItems)
                } else {
                    Toast.makeText(this@GrainsCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Grains>>, t: Throwable) {
                Log.e("GrainsCategory", "Error fetching items: ${t.message}", t)
                Toast.makeText(this@GrainsCategory, "Failed to fetch items: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterItems(query: String) {
        val filteredList = foodItems.filter { it.name.contains(query, true) }
        binding.recyclerView.visibility = if (filteredList.isNotEmpty()) View.VISIBLE else View.GONE
        adapter.updateItems(filteredList)
    }

    private fun onFoodItemSelected(item: Grains) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            item.isSelected = false
            Toast.makeText(this, "${item.name} deselected", Toast.LENGTH_SHORT).show()
        } else {
            selectedItems.add(item)
            item.isSelected = true
            Toast.makeText(this, "${item.name} selected", Toast.LENGTH_SHORT).show()
        }

        // Update the adapter to refresh the item view
        val index = adapter.currentList.indexOf(item)
        if (index != -1) {
            adapter.notifyItemChanged(index)
        } else {
            Log.e("GrainsCategory", "The selected item was not found in the current list!")
        }
    }

    private fun saveSelectedItems() {
        // Fetch existing items from SharedPreferences
        val sharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
        val existingItemsString = sharedPreferences.getString("PantryItems", "") ?: ""

        // Split existing items into a list, accounting for empty strings
        val existingItemsList = existingItemsString.split("|").filter { it.isNotEmpty() }.toMutableList()

        // Append selected items to existing items
        selectedItems.forEach { item ->
            val pantryItem = "${item.name},${item.quantity ?: "N/A"}" // Customize format as needed
            existingItemsList.add(pantryItem) // Adding new items to existing list
            Toast.makeText(this, "${item.name} saved to pantry", Toast.LENGTH_SHORT).show()
        }

        // Join the updated pantry items into a single string
        val updatedPantryItems = existingItemsList.joinToString("|")

        // Save the updated string back to SharedPreferences
        sharedPreferences.edit().putString("PantryItems", updatedPantryItems).apply()

        // Clear the list of selected items
        selectedItems.clear()

        // Reset the adapter's items (if needed, may not be necessary)
        adapter.updateItems(foodItems)

        navigateAfterSave()  // Navigate to pantry after saving
    }

    private fun navigateAfterSave() {
        val intent = Intent(this, Categories::class.java)
        intent.putExtra("showFragment", "pantry") // optionally open pantry
        startActivity(intent)
        finish()
    }
}
