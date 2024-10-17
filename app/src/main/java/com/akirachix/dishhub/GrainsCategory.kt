






package com.akirachix.dishhub

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityGrainsCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GrainsCategory : AppCompatActivity() {

    private lateinit var binding: ActivityGrainsCategoryBinding
    private lateinit var adapter: GrainsAdapter
    private var foodItems: List<Grains> = listOf()

    @SuppressLint("MissingInflatedId")
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
        adapter = GrainsAdapter(emptyList()) { item -> onFoodItemSelected(item) }
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

    private fun fetchFoodItems() {
        GrainsRetrofitInstance.api.getFoodItems().enqueue(object : Callback<List<Grains>> {
            override fun onResponse(call: Call<List<Grains>>, response: Response<List<Grains>>) {
                if (response.isSuccessful) {
                    foodItems = response.body() ?: emptyList()
                    adapter.updateItems(foodItems)
                } else {
                    Toast.makeText(this@GrainsCategory, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Grains>>, t: Throwable) {
                Toast.makeText(this@GrainsCategory, "Failed to fetch items: ${t.message}", Toast.LENGTH_SHORT).show()
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

    private fun setupSaveButton() {
        binding.button.setOnClickListener {
            val selectedItems = adapter.getSelectedItems()
            if (selectedItems.isNotEmpty()) {
                Toast.makeText(this, "Saved ${selectedItems.size} items to pantry", Toast.LENGTH_SHORT).show()
                // TODO: Implement saving mechanism (e.g., database or shared preferences)
            } else {
                Toast.makeText(this, "No items selected to save", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onFoodItemSelected(item: Grains) {
        Toast.makeText(this, "You selected: ${item.name}", Toast.LENGTH_SHORT).show()
        binding.searchView.setText(item.name)
        binding.recyclerView.visibility = View.GONE
    }
}