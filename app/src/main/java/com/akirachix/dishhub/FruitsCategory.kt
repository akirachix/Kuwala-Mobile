//
//
//package com.akirachix.dishhub
//
//import FruitsAdapter
//import android.annotation.SuppressLint
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.akirachix.dishhub.databinding.ActivityFruitsCategoryBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//@Suppress("DEPRECATION")
//class FruitsCategory : AppCompatActivity() {
//
//    private lateinit var binding: ActivityFruitsCategoryBinding
//    private lateinit var adapter: FruitsAdapter
//    private var foodItems: List<Fruits> = listOf()
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityFruitsCategoryBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//        setupSearchView()
//        setupBackButton()
//        fetchFoodItems()
//
//        // Handle the button click for saving to pantry
//        binding.button.setOnClickListener { saveItemsToPantry() }
//    }
//
//    private fun setupRecyclerView() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = FruitsAdapter(emptyList()) { item -> onFoodItemSelected(item) }
//        binding.recyclerView.adapter = adapter
//    }
//
//    private fun setupSearchView() {
//        binding.searchView.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                filterItems(s.toString())
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//    private fun setupBackButton() {
//        binding.imageView.setOnClickListener { onBackPressed() }
//    }
//
//    private fun fetchFoodItems() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://dishhub-2ea9d6ca8e11.herokuapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(FruitsApiService::class.java)
//        service.getFoodItems().enqueue(object : Callback<List<Fruits>> {
//            override fun onResponse(call: Call<List<Fruits>>, response: Response<List<Fruits>>) {
//                if (response.isSuccessful) {
//                    foodItems = response.body() ?: emptyList()
//                    adapter.updateItems(foodItems)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Fruits>>, t: Throwable) {
//                Toast.makeText(this@FruitsCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun filterItems(query: String) {
//        val filteredList = foodItems.filter { it.name.contains(query, true) }
//        if (filteredList.isNotEmpty()) {
//            binding.recyclerView.visibility = View.VISIBLE
//            adapter.updateItems(filteredList)
//        } else {
//            binding.recyclerView.visibility = View.GONE
//        }
//    }
//
//    private fun onFoodItemSelected(item: Fruits) {
//        Toast.makeText(this, "You selected: ${item.name}", Toast.LENGTH_SHORT).show()
//        binding.searchView.setText(item.name)
//        binding.recyclerView.visibility = View.GONE
//    }
//
//    private fun saveItemsToPantry() {
//        val selectedItems = foodItems.filter { it.isSelected }
//        if (selectedItems.isNotEmpty()) {
//            val itemNames = selectedItems.joinToString(", ") { it.name }
//            Toast.makeText(this, "Saved to Pantry: $itemNames", Toast.LENGTH_SHORT).show()
//
//            // Save to SharedPreferences
//            val sharedPreferences: SharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//
//
//            val existingItems = sharedPreferences.getString("PantryItems", "") ?: ""
//            val newItems = selectedItems.joinToString("|") { "${it.name},fruit,${it.quantity}" }
//
//            // Save combined items
//            editor.putString("PantryItems", if (existingItems.isNotEmpty()) "$existingItems|$newItems" else newItems)
//            editor.apply()
//
//
//            selectedItems.forEach { it.isSelected = false }  // Reset selection state
//            adapter.updateItems(foodItems)  // Update the adapter to refresh the view
//        } else {
//            Toast.makeText(this, "No items selected to save.", Toast.LENGTH_SHORT).show()
//        }
//    }
//}






package com.akirachix.dishhub
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.ActivityFruitsCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class FruitsCategory : AppCompatActivity() {

    private lateinit var binding: ActivityFruitsCategoryBinding
    private lateinit var adapter: FruitsAdapter
    private var foodItems: List<Fruits> = listOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFruitsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupBackButton()
        fetchFoodItems()

        // Handle the button click for saving to pantry
        binding.button.setOnClickListener { saveItemsToPantry() }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FruitsAdapter(foodItems) { item -> onFoodItemSelected(item) }
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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dishhub-2ea9d6ca8e11.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FruitsApiService::class.java)
        service.getFoodItems().enqueue(object : Callback<List<Fruits>> {
            override fun onResponse(call: Call<List<Fruits>>, response: Response<List<Fruits>>) {
                if (response.isSuccessful) {
                    foodItems = response.body() ?: emptyList()
                    adapter.updateItems(foodItems)
                }
            }

            override fun onFailure(call: Call<List<Fruits>>, t: Throwable) {
                Toast.makeText(this@FruitsCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterItems(query: String) {
        val filteredList = foodItems.filter { it.name.contains(query, true) }
        adapter.updateItems(filteredList)
    }

    private fun onFoodItemSelected(item: Fruits) {
        Toast.makeText(this, "You selected: ${item.name}", Toast.LENGTH_SHORT).show()
        binding.searchView.setText(item.name)
    }

    private fun saveItemsToPantry() {
        val selectedItems = foodItems.filter { it.isSelected }
        if (selectedItems.isNotEmpty()) {
            val itemNames = selectedItems.joinToString(", ") { it.name }
            Toast.makeText(this, "Saved to Pantry: $itemNames", Toast.LENGTH_SHORT).show()

            // Save to SharedPreferences
            val sharedPreferences: SharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val existingItems = sharedPreferences.getString("PantryItems", "") ?: ""
            val newItems = selectedItems.joinToString("|") { "${it.name},fruit,${it.quantity}" }

            // Save combined items
            editor.putString("PantryItems", if (existingItems.isNotEmpty()) "$existingItems|$newItems" else newItems)
            editor.apply()

            selectedItems.forEach { it.isSelected = false }  // Reset selection state
            adapter.updateItems(foodItems)  // Update the adapter to refresh the view
        } else {
            Toast.makeText(this, "No items selected to save.", Toast.LENGTH_SHORT).show()
        }
    }
}
