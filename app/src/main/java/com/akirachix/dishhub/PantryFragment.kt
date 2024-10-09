
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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.api.RecipeInformation
import com.akirachix.dishhub.api.RecipesResponse
import com.akirachix.dishhub.api.RetrofitClientSpoonacular
import com.akirachix.dishhub.databinding.FragmentPantryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PantryFragment : Fragment() {
    private lateinit var binding: FragmentPantryBinding
    private val selectedIngredients: MutableList<String> = mutableListOf()
    private lateinit var pantryAdapter: PantryAdapter

    private val ADD_ITEM_REQUEST_CODE = 1

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
            if (selectedIngredients.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one ingredient", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            retrieveRecipes(selectedIngredients)
        }

        binding.root.setOnClickListener {
            val intent = Intent(requireContext(), AddItemManually::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE)
        }
    }


    private fun loadPantryItems() {
        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
        val pantryItemsString = sharedPreferences.getString("PantryItems", "")

        pantryItemsString?.let {
            if (it.isNotEmpty()) {
                val itemsArray = it.split("|")
                PantryRepository.pantryItems.clear()
                for (item in itemsArray) {
                    val parts = item.split(",")
                    if (parts.size >= 3) {
                        val foodName = parts[0]
                        try {
                            val quantity = parts[2].toInt() // Ensure you're getting the quantity
                            PantryRepository.pantryItems.add(PantryItems(foodName, quantity))
                        } catch (e: NumberFormatException) {
                            Log.e("PantryFragment", "Error parsing quantity for $foodName", e)
                        }
                    }
                }
                pantryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
        pantryAdapter = PantryAdapter(PantryRepository.pantryItems, selectedIngredients)
        binding.rvpantry.adapter = pantryAdapter
    }

    private fun updatePantryWithNewItem(item: String) {
        val parts = item.split(",")
        if (parts.size >= 3) {
            val foodName = parts[0]
            val quantity = parts[2].toInt()
            PantryRepository.pantryItems.add(PantryItems(foodName, quantity))
            pantryAdapter.notifyItemInserted(PantryRepository.pantryItems.size - 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            data?.getStringExtra("newItem")?.let {
                updatePantryWithNewItem(it)
            }
        }
    }

    private fun cleanHtmlTags(text: String): String {
        return text
            .replace(Regex("<[^>]*>"), "") // Remove HTML tags
            .replace(" ", " ")
            .replace("&", "&")
            .replace("<", "<")
            .replace(">", ">")
            .replace("\n", " ") // Replace newlines with spaces
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private fun retrieveRecipes(ingredients: List<String>) {
        val loadingDialog = AlertDialog.Builder(requireContext())
            .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
            .setCancelable(false)
            .create()
        loadingDialog.show()

        val ingredientsQuery = ingredients.joinToString(",")
        val apiService = RetrofitClientSpoonacular.instance

        apiService.getRecipesByIngredients(ingredientsQuery, "07d5c6ca83604b57aef6a720e80f378b")
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

                        val recipeDetails = mutableListOf<RecipeDetailsDisplay>()
                        var completedRequests = 0

                        recipes.forEach { recipe ->
                            apiService.getRecipeInformation(recipe.id)
                                .enqueue(object : Callback<RecipeInformation> {
                                    override fun onResponse(
                                        call: Call<RecipeInformation>,
                                        detailResponse: Response<RecipeInformation>
                                    ) {
                                        completedRequests++

                                        if (detailResponse.isSuccessful) {
                                            val recipeInfo = detailResponse.body()
                                            recipeInfo?.let {
                                                val allIngredients = recipe.usedIngredients.map { it.name } +
                                                        recipe.missedIngredients.map { it.name }

                                                val recipeDetail = RecipeDetailsDisplay(
                                                    cleanHtmlTags(recipe.title),
                                                    cleanHtmlTags(allIngredients.joinToString(", ")),
                                                    cleanHtmlTags(it.instructions ?: "No instructions available")
                                                )
                                                recipeDetails.add(recipeDetail)
                                            }
                                        }

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

                                    override fun onFailure(call: Call<RecipeInformation>, t: Throwable) {
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
                    Toast.makeText(requireContext(), "Error: ${t.message}",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }
}







