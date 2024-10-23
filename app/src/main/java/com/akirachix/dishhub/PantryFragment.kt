

package com.akirachix.dishhub

import RecipeDetailsDisplay
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.api.EdamamApiResponse
import com.akirachix.dishhub.databinding.FragmentPantryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.collections.mutableListOf as mutableListOf1

class PantryFragment : Fragment() {
    private lateinit var binding: FragmentPantryBinding
    private val selectedIngredients: MutableList<String> = mutableListOf1()
    private lateinit var pantryAdapter: PantryAdapter
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPantryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPantryItems()

        binding.btnYumAhead.setOnClickListener {
            if (selectedIngredients.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one ingredient", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveSelectedVegetables()
            retrieveRecipes(selectedIngredients)
        }
    }

    private fun setupRecyclerView() {
        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
        pantryAdapter = PantryAdapter(requireContext(), mutableListOf1(), selectedIngredients) {
            saveSelectedVegetables()
        }
        binding.rvpantry.adapter = pantryAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                pantryAdapter.filter(newText ?: "")
                return true
            }
        })
    }

    private fun fetchPantryItems() {
        ApiClient.pantryApiService.getPantryItems().enqueue(object : Callback<List<PantryItems>> {
            override fun onResponse(call: Call<List<PantryItems>>, response: Response<List<PantryItems>>) {
                if (response.isSuccessful) {
                    val pantryItems = response.body()
                    pantryItems?.let {
                        println("Debug: Fetched pantry items - ${it.map { item -> item.item}}")
                        pantryAdapter.updateItems(it)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch pantry items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PantryItems>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveSelectedVegetables() {
        val sharedPreferences = requireContext().getSharedPreferences("PantryPrefs", Context.MODE_PRIVATE)
        val jsonSelectedIngredients = gson.toJson(selectedIngredients)
        sharedPreferences.edit().putString("selected_ingredients", jsonSelectedIngredients).apply()
    }

    private fun loadSelectedVegetables() {
        val sharedPreferences = requireContext().getSharedPreferences("PantryPrefs", Context.MODE_PRIVATE)
        val jsonSelectedIngredients = sharedPreferences.getString("selected_ingredients", null)
        if (jsonSelectedIngredients != null) {
            val type = object : TypeToken<List<String>>() {}.type
            val loadedSelectedIngredients: List<String> = gson.fromJson(jsonSelectedIngredients, type)
            selectedIngredients.clear()
            selectedIngredients.addAll(loadedSelectedIngredients)
        }
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

private fun Any.getString(s: String, nothing: Nothing?) {
}

private fun Any.fromJson(jsonSelectedIngredients: Any, type: Type?) {

}










