







package com.akirachix.dishhub

import PantryAdapter
import RecipeDetailsDisplay
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.api.RecipeInformation
import com.akirachix.dishhub.api.RecipesResponse
import com.akirachix.dishhub.api.RetrofitClientSpoonacular
import com.akirachix.dishhub.databinding.FragmentPantryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.List




class PantryFragment : Fragment() {
    private lateinit var binding: FragmentPantryBinding
    private val selectedIngredients: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPantryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPantryItems()

        binding.btnYumAhead.setOnClickListener {
            retrieveRecipes(selectedIngredients)
        }
    }

    private fun loadPantryItems() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
        val pantryItemsString = sharedPreferences.getString("PantryItems", "")

        pantryItemsString?.let {
            if (it.isNotEmpty()) {
                val itemsArray = it.split("|")
                for (item in itemsArray) {
                    val parts = item.split(",")
                    if (parts.size == 3) {
                        val foodName = parts[0]
                        try {
                            val quantity = parts[1] // Ensure this is a valid integer
                            PantryRepository.pantryItems.add(PantryItems(0, foodName, 1))
                        } catch (e: NumberFormatException) {
                            Toast.makeText(
                                requireContext(),
                                "Invalid quantity for food item: $foodName",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Invalid pantry item format: $item",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                (binding.rvpantry.adapter as? PantryAdapter)?.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
        val pantryAdapter = PantryAdapter(PantryRepository.pantryItems, selectedIngredients)
        binding.rvpantry.adapter = pantryAdapter
    }




    private fun retrieveRecipes(ingredients: List<String>) {
        val loadingDialog = AlertDialog.Builder(requireContext())
            .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
            .setCancelable(false)
            .create()
        loadingDialog.show()

        val ingredientsQuery = ingredients.joinToString(",")
        val apiService = RetrofitClientSpoonacular.instance

        apiService.getRecipesByIngredients(ingredientsQuery ,  "b2312a182166424a8e94029529542401")

            .enqueue(object : Callback<List<RecipesResponse>> {
                override fun onResponse(
                    call: Call<List<RecipesResponse>>,
                    response: Response<List<RecipesResponse>>
                ) {
                    if (response.isSuccessful) {
                        val recipes = response.body()
                        Log.d("PantryFragment", "Recipes: $recipes")

                        if (recipes.isNullOrEmpty()) {
                            loadingDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                "No recipes found for selected ingredients",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                        // Fetch detailed information for each recipe
                        val recipeDetails = mutableListOf<RecipeDetailsDisplay>()
                        var completedRequests = 0

                        recipes.forEach { recipe ->
                            apiService.getRecipeInformation(recipe.id)
                                .enqueue(object : Callback<RecipeInformation> {
                                    private val missedIngredients: Any
                                        get() {
                                            TODO()
                                        }


                                    override fun onResponse(
                                        call: Call<RecipeInformation>,
                                        detailResponse: Response<RecipeInformation>
                                    ) {
                                        completedRequests++

                                        if (detailResponse.isSuccessful) {
                                            val recipeInfo = detailResponse.body()
                                            recipeInfo?.let {
                                                // Get the names of both used and missed ingredients
                                                val allIngredients = recipe.usedIngredients.map { it.name } +
                                                        recipe.missedIngredients.map { it.name }  // Use missedIngredients properly

                                                val recipeDetail = RecipeDetailsDisplay(
                                                    recipe.title,
                                                    allIngredients.joinToString(", "),  // Join all ingredient names into a single string
                                                    it.instructions ?: "No instructions available"
                                                )
                                                recipeDetails.add(recipeDetail)
                                            }
                                        }


                                        // Check if all requests are completed
                                        if (completedRequests == recipes.size) {
                                            loadingDialog.dismiss()
                                            if (recipeDetails.isNotEmpty()) {
                                                val intent = Intent(
                                                    requireContext(),
                                                    RecipeDisplay::class.java
                                                ).apply {
                                                    putParcelableArrayListExtra(
                                                        "RECIPES_LIST",
                                                        ArrayList(recipeDetails)
                                                    )
                                                }
                                                startActivity(intent)
                                            } else {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Failed to fetch recipe details",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<RecipeInformation
                                                >,
                                        t: Throwable
                                    ) {
                                        completedRequests++
                                        if (completedRequests == recipes.size) {
                                            loadingDialog.dismiss()
                                            Toast.makeText(
                                                requireContext(),
                                                "Error: ${t.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                })
                        }
                    } else {
                        loadingDialog.dismiss()
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch recipes: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<RecipesResponse>>, t: Throwable) {
                    loadingDialog.dismiss()
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}


private fun Any.enqueue(callback: Callback<RecipeInformation>) {

}





