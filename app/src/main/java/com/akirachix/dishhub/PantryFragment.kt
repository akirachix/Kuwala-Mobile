





package com.akirachix.dishhub

import PantryAdapter
import RecipeDetailsDisplay
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.FragmentPantryBinding
import com.akirachix.dishhub.api.EdamamApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PantryFragment : Fragment() {

    private lateinit var binding: FragmentPantryBinding
    private val selectedIngredients = mutableListOf<String>()
    private lateinit var pantryAdapter: PantryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPantryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupFetchRecipeButton()
        loadPantryItems()

        // Set up back arrow click listener
        binding.backArrow.setOnClickListener {
            requireActivity().onBackPressed()  // Handle back navigation
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
        pantryAdapter = PantryAdapter(mutableListOf(), selectedIngredients) {
            savePantryItems()
        }
        binding.rvpantry.adapter = pantryAdapter
    }

    private fun savePantryItems() {
        // TODO: Implement save pantry items logic.
    }

    private fun setupFetchRecipeButton() {
        binding.btnYumAhead.setOnClickListener {
            if (selectedIngredients.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one ingredient", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            retrieveRecipes(selectedIngredients)
        }
    }

    private fun loadPantryItems() {
        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
        val pantryItemsString = sharedPreferences.getString("PantryItems", "") ?: ""
        val pantryItems = pantryItemsString.split("|").mapNotNull {
            val parts = it.split(",")
            if (parts.size >= 2) {
                val name = parts[0]
                val quantity = parts[1].toIntOrNull() ?: 1
                PantryItems(name, quantity.toString(), quantity.toString(), "", "")
            } else null
        }
        pantryAdapter.updateItems(pantryItems)
    }

    private fun retrieveRecipes(ingredients: List<String>) {
        val loadingDialog = AlertDialog.Builder(requireContext())
            .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
            .setCancelable(false)
            .create()
        loadingDialog.show()

        val ingredientsQuery = ingredients.joinToString(",")
        val appId = "5eb7482f"
        val appKey = "8c7bab15682f102689971468389f9f52"

        val apiService = RetrofitClientEdamam.instance
        apiService.getRecipesByIngredients(ingredientsQuery, appId, appKey)
            .enqueue(object : Callback<EdamamApiResponse> {
                override fun onResponse(call: Call<EdamamApiResponse>, response: Response<EdamamApiResponse>) {
                    loadingDialog.dismiss()
                    if (response.isSuccessful) {
                        val recipes = response.body()?.hits ?: return
                        val recipeDetails = recipes.map { hit ->
                            val recipe = hit.recipe
                            RecipeDetailsDisplay(
                                recipe.label,
                                recipe.ingredientLines.joinToString("\n"),
                                recipe.instructions ?: "Instructions not available",
                                recipe.url,
                                recipe.image
                            )
                        }
                        if (recipeDetails.isNotEmpty()) {
                            val intent = Intent(requireContext(), RecipeDisplay::class.java).apply {
                                putParcelableArrayListExtra("RECIPES_LIST", ArrayList(recipeDetails))
                            }
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to fetch recipes", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<EdamamApiResponse>, t: Throwable) {
                    loadingDialog.dismiss()
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

}