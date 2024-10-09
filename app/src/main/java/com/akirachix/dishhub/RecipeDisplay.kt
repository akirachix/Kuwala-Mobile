//package com.akirachix.dishhub
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.akirachix.dishhub.databinding.ActivityRecipeDisplayBinding
//import com.akirachix.dishhub.model.Recipe
//
//
//class RecipeDisplay : AppCompatActivity() {
//
//    private lateinit var binding: ActivityRecipeDisplayBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRecipeDisplayBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Get data from the intent
//        val recipeList = intent.getParcelableArrayListExtra<Recipe>("RECIPES_LIST") ?: arrayListOf()
//
//        // Assuming you want to display them in a RecyclerView or similar
//        val recipeTitles = recipeList.joinToString("\n")  // Customize this to your needs
//        binding.titleTextView.text = recipeTitles // Displaying titles for now
//    }
//}


//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.akirachix.dishhub.R
//import com.akirachix.dishhub.RecipeAdapter
//import com.akirachix.dishhub.model.Recipe
//
//class RecipeDisplay : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_recipe_display)
//
//        recyclerView = findViewById(R.id.recyclerView)
//
//        // Safely get the recipes from the Intent, handling null values
//        val recipes: ArrayList<Recipe> = intent.getParcelableArrayListExtra<Recipe>("RECIPES_LIST") ?: ArrayList()
//
//        // Initialize the adapter with a non-nullable ArrayList of Recipe
//        val recipeAdapter = RecipeAdapter(recipes)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = recipeAdapter
//    }
//}




//
//package com.akirachix.dishhub
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.akirachix.dishhub.R
//import com.akirachix.dishhub.model.Recipe  // Make sure you have a Recipe model class
//
//class RecipeDisplay : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_recipe_display)
//
//        recyclerView = findViewById(R.id.recyclerView)
//
//        // Safely get the recipes from the Intent
//        val recipes: ArrayList<Recipe> = intent.getParcelableArrayListExtra<Recipe>("RECIPES_LIST") ?: ArrayList()
//
//        // Initialize the adapter with a non-nullable ArrayList of Recipe
//        val recipeAdapter = RecipeAdapter(recipes)  // Ensure you have the RecipeAdapter implementation
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = recipeAdapter
//    }
//}
//





//
//package com.akirachix.dishhub
//
//import RecipeDetailsDisplay
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.akirachix.dishhub.databinding.ActivityRecipeDisplayBinding
//
//
//
//
//
//class RecipeDisplay : AppCompatActivity() {
//    private lateinit var binding: ActivityRecipeDisplayBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRecipeDisplayBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Get the list of recipes passed from PantryFragment
//        val recipesList = intent.getParcelableArrayListExtra<RecipeDetailsDisplay>("RECIPES_LIST")
//
//        setupRecyclerView(recipesList ?: emptyList())
//    }
//
//    private fun setupRecyclerView(recipesList: List<RecipeDetailsDisplay>) {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        val recipeAdapter = RecipeAdapter(recipesList)
//        binding.recyclerView.adapter = recipeAdapter
//    }
//}










package com.akirachix.dishhub

import RecipeAdapter
import RecipeDetailsDisplay
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.databinding.ActivityRecipeDisplayBinding


class RecipeDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recipe Suggestions"

        val recipes = intent.getParcelableArrayListExtra<RecipeDetailsDisplay>("RECIPES_LIST")
        recipes?.let {
            binding.recipesRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@RecipeDisplay)
                adapter = RecipeAdapter(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

