
package com.akirachix.dishhub

import RecipeDetailsDisplay
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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







