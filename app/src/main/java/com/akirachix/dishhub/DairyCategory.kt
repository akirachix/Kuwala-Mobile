//package com.akirachix.dishhub
//
//import DairyAdapter
//import DairyApiService
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.akirachix.dishhub.databinding.ActivityDairyCategoryBinding
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//@Suppress("DEPRECATION")
//class DairyCategory : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDairyCategoryBinding
//    private lateinit var adapter: DairyAdapter
//    private var foodItems: List<Dairy> = listOf()
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityDairyCategoryBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//        setupSearchView()
//        setupBackButton()
//        fetchFoodItems()
//    }
//
//    private fun setupRecyclerView() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = DairyAdapter(emptyList()) { item -> onFoodItemSelected(item) }
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
//        binding.imageView.setOnClickListener {
//            onBackPressed()
//        }
//    }
//
//    private fun fetchFoodItems() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://dishhub-2ea9d6ca8e11.herokuapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(DairyApiService::class.java)
//        service.getFoodItems().enqueue(object : Callback<List<Dairy>> {
//            override fun onResponse(call: Call<List<Dairy>>, response: Response<List<Dairy>>) {
//                if (response.isSuccessful) {
//                    foodItems = response.body() ?: emptyList()
//                    adapter.updateItems(foodItems)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Dairy>>, t: Throwable) {
//                Toast.makeText(this@DairyCategory, "Failed to fetch items", Toast.LENGTH_SHORT).show()
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
//    private fun onFoodItemSelected(item: Dairy) {
//        Toast.makeText(this, "You selected: ${item.name}", Toast.LENGTH_SHORT).show()
//        binding.searchView.setText(item.name)
//        binding.recyclerView.visibility = View.GONE
//    }
//}









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


class DairyCategory: AppCompatActivity() {

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

    private fun fetchFoodItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dishhub-2ea9d6ca8e11.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        service.getFruits().enqueue(object : Callback<List<Dairy>> {
            override fun onResponse(call: Call<List<Dairy>>, response: Response<List<Dairy>>) {
                if (response.isSuccessful) {
                    foodItems = (response.body() ?: emptyList()) as List<Dairy>
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
        selectedItems.forEach { Dairy ->
            val pantryItem = PantryItems(
//                id = Dairy.id,
                name = Dairy.name,
                quantity = Dairy.quantity,

                )
            PantryRepository.addPantryItem(pantryItem) // Method to add item to pantry

            Toast.makeText(this, "${pantryItem.name} saved to pantry", Toast.LENGTH_SHORT).show()
        }

        selectedItems.clear()
    }
}


private fun Any.enqueue(callback: Callback<List<Dairy>>) {

}


