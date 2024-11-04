
package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
        fetchFoodItems()
        setupSaveButton() // Set up the save button
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DairyAdapter(emptyList(), ::onFoodItemSelected, null)
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
                saveSelectedItems() // Call the function to save selected items and navigate to pantry
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
        service.getDairyItems().enqueue(object : Callback<List<Dairy>> {
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
        binding.recyclerView.visibility = if (filteredList.isNotEmpty()) View.VISIBLE else View.GONE
        adapter.updateItems(filteredList)
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

        val currentList: Any? = adapter.currentList
        val index = currentList.indexOf(item)
        if (index >= 0) {
            adapter.notifyItemChanged(index)
        } else {
            Log.e("DairyCategory", "The selected item was not found in the current list!")
        }
    }

    private fun saveSelectedItems() {
        val sharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val existingItems = sharedPreferences.getString("PantryItems", "") ?: ""

        selectedItems.forEach { dairy ->
            val pantryItem = "${dairy.name},${dairy.quantity ?: "N/A"}"
            val newItems = if (existingItems.isNotEmpty()) {
                "$existingItems|$pantryItem"
            } else {
                pantryItem
            }
            editor.putString("PantryItems", newItems)

            Toast.makeText(this, "${dairy.name} saved to pantry", Toast.LENGTH_SHORT).show()
        }

        editor.apply()
        selectedItems.clear()

        // Call to navigate to pantry after saving
        navigateAfterSave()
    }

    private fun navigateAfterSave() {
        // Switch to the Categories activity and show the pantry segment
        val intent = Intent(this, Categories::class.java)
        intent.putExtra("showFragment", "pantry") // optionally open pantry
        startActivity(intent)
        finish()
    }
}

private fun Any?.indexOf(item: Dairy): Int {
    // Cast 'this' to List<Dairy> or return -1 if the cast fails
    val list = this as? List<Dairy> ?: return -1
    // Return the index of the item or -1 if not found
    return list.indexOf(item)
}