






//Edamman

//
//package com.akirachix.dishhub
//
//import PantryAdapter
//import RecipeDetailsDisplay
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.SearchView
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.akirachix.dishhub.databinding.FragmentPantryBinding
//import com.akirachix.dishhub.api.EdamamApiResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class PantryFragment : Fragment() {
//    private lateinit var binding: FragmentPantryBinding
//    private val selectedIngredients: MutableList<String> = mutableListOf()
//    private lateinit var pantryAdapter: PantryAdapter
//    private val ADD_ITEM_REQUEST_CODE = 1
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentPantryBinding.inflate(inflater, container, false)
//        setupRecyclerView()
//        setupSearchView()
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        loadPantryItems()
//
//        binding.btnYumAhead.setOnClickListener {
//            if (selectedIngredients.isEmpty()) {
//                Toast.makeText(requireContext(), "Please select at least one ingredient", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            saveSelectedVegetables()  // Save selected vegetables before retrieving recipes
//            retrieveRecipes(selectedIngredients)
//        }
//    }
//
//    private fun setupRecyclerView() {
//        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
//        pantryAdapter = PantryAdapter(mutableListOf(), selectedIngredients) {
//            savePantryItems()
//        }
//        binding.rvpantry.adapter = pantryAdapter
//    }
//
//    private fun setupSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                pantryAdapter.filter(newText ?: "")
//                return true
//            }
//        })
//    }
//
//    private fun loadPantryItems() {
//        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
//        val pantryItemsString = sharedPreferences.getString("PantryItems", "")
//        val pantryItems = mutableListOf<PantryItems>()
//
//        pantryItemsString?.let {
//            if (it.isNotEmpty()) {
//                val itemsArray = it.split("|")
//                for (item in itemsArray) {
//                    val parts = item.split(",")
//                    if (parts.size >= 2) {
//                        val foodName = parts[0].trim()
//                        val quantityString = parts[1].trim()
//                        val quantity = quantityString.toIntOrNull() ?: 1
//                        pantryItems.add(PantryItems(foodName, quantity, it.quantity, "", ""))
//                    }
//                }
//            }
//        }
//        pantryAdapter.updateItems(pantryItems)
//    }
//
//    private fun savePantryItems() {
//        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val pantryItemsString = pantryAdapter.getPantryItems().joinToString("|") { "${it.name},${it.quantity}" }
//        editor.putString("PantryItems", pantryItemsString)
//        editor.apply()
//    }
//
//    private fun saveSelectedVegetables() {
//        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val currentItems = sharedPreferences.getString("PantryItems", "") ?: ""
//        val newVegetables = selectedIngredients.joinToString("|") { "$it,1" }
//        val updatedItems = if (currentItems.isNotEmpty()) "$currentItems|$newVegetables" else newVegetables
//        editor.putString("PantryItems", updatedItems)
//        editor.apply()
//    }
//
//    private fun retrieveRecipes(ingredients: List<String>) {
//        val loadingDialog = AlertDialog.Builder(requireContext())
//            .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
//            .setCancelable(false)
//            .create()
//        loadingDialog.show()
//
//        val ingredientsQuery = ingredients.joinToString(",")
//        val appId = "5eb7482f"
//        val appKey = "8c7bab15682f102689971468389f9f52"
//
//        val apiService = RetrofitClientEdamam.instance
//        apiService.getRecipesByIngredients(ingredientsQuery, appId, appKey)
//            .enqueue(object : Callback<EdamamApiResponse> {
//                override fun onResponse(call: Call<EdamamApiResponse>, response: Response<EdamamApiResponse>) {
//                    loadingDialog.dismiss()
//                    if (response.isSuccessful) {
//                        val recipes = response.body()?.hits ?: return
//                        val recipeDetails = recipes.map { hit ->
//                            val recipe = hit.recipe
//                            RecipeDetailsDisplay(
//                                recipe.label,
//                                recipe.ingredientLines.joinToString("\n"),
//                                recipe.instructions ?: "Instructions not available",
//                                recipe.url,
//                                recipe.image
//                            )
//                        }
//                        if (recipeDetails.isNotEmpty()) {
//                            val intent = Intent(requireContext(), RecipeDisplay::class.java).apply {
//                                putParcelableArrayListExtra("RECIPES_LIST", ArrayList(recipeDetails))
//                            }
//                            startActivity(intent)
//                        }
//                    } else {
//                        Toast.makeText(requireContext(), "Failed to fetch recipes", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<EdamamApiResponse>, t: Throwable) {
//                    loadingDialog.dismiss()
//                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//}





package com.akirachix.dishhub

import PantryAdapter
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
import com.akirachix.dishhub.databinding.FragmentPantryBinding
import com.akirachix.dishhub.api.EdamamApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val String.quantity: Int
    get() {
        return this.length
    }
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
        setupSearchView()
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
            saveSelectedVegetables()  // Save selected vegetables before retrieving recipes
            retrieveRecipes(selectedIngredients)
        }
    }

    private fun setupRecyclerView() {
        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
        pantryAdapter = PantryAdapter(mutableListOf(), selectedIngredients) {
            savePantryItems()
        }
        binding.rvpantry.adapter = pantryAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pantryAdapter.filter(newText ?: "")
                return true
            }
        })
    }

    private fun loadPantryItems() {
        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
        val pantryItemsString = sharedPreferences.getString("PantryItems", "")
        val pantryItems = mutableListOf<PantryItems>()

        pantryItemsString?.let {
            if (it.isNotEmpty()) {
                val itemsArray = it.split("|")
                for (item in itemsArray) {
                    val parts = item.split(",")
                    if (parts.size >= 2) {
                        val foodName = parts[0].trim()
                        val quantityString = parts[1].trim()
                        val quantity = quantityString.toIntOrNull() ?: 1
                        pantryItems.add(PantryItems(foodName,
                            quantity.toString(), it.quantity, "", ""))
                    }
                }
            }
        }
        pantryAdapter.updateItems(pantryItems)
    }

    private fun savePantryItems() {
        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val pantryItemsString = pantryAdapter.getPantryItems().joinToString("|") { "${it.name},${it.quantity}" }
        editor.putString("PantryItems", pantryItemsString)
        editor.apply()
    }

    private fun saveSelectedVegetables() {
        val sharedPreferences = requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentItems = sharedPreferences.getString("PantryItems", "") ?: ""
        val newVegetables = selectedIngredients.joinToString("|") { "$it,1" }
        val updatedItems = if (currentItems.isNotEmpty()) "$currentItems|$newVegetables" else newVegetables
        editor.putString("PantryItems", updatedItems)
        editor.apply()
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

//
//package com.akirachix.dishhub
//
//import PantryAdapter
//import RecipeDetailsDisplay
//import RecipesResponse
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.SearchView
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.akirachix.dishhub.databinding.FragmentPantryBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class PantryFragment : Fragment() {
//    private lateinit var binding: FragmentPantryBinding
//    private val selectedIngredients: MutableList<String> = mutableListOf()
//    private lateinit var pantryAdapter: PantryAdapter
//    private val ADD_ITEM_REQUEST_CODE = 1
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentPantryBinding.inflate(inflater, container, false)
//        setupRecyclerView()
//        setupSearchView()
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        loadPantryItems()
//
//        binding.btnYumAhead.setOnClickListener {
//            if (selectedIngredients.isEmpty()) {
//                Toast.makeText(
//                    requireContext(),
//                    "Please select at least one ingredient",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }
//            saveSelectedVegetables()  // Save selected vegetables before retrieving recipes
//            retrieveRecipes(selectedIngredients)
//        }
//    }
//
//    private fun setupRecyclerView() {
//        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
//        pantryAdapter = PantryAdapter(mutableListOf(), selectedIngredients) {
//            savePantryItems()
//        }
//        binding.rvpantry.adapter = pantryAdapter
//    }
//
//    private fun setupSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                pantryAdapter.filter(newText ?: "")
//                return true
//            }
//        })
//    }
//
//    private fun loadPantryItems() {
//        val sharedPreferences =
//            requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
//        val pantryItemsString = sharedPreferences.getString("PantryItems", "")
//        val pantryItems = mutableListOf<PantryItems>()
//
//        pantryItemsString?.let {
//            if (it.isNotEmpty()) {
//                val itemsArray = it.split("|")
//                for (item in itemsArray) {
//                    val parts = item.split(",")
//                    if (parts.size >= 2) {
//                        val foodName = parts[0].trim()
//                        val quantityString = parts[1].trim()
//                        val quantity = quantityString.toIntOrNull() ?: 1
//                        pantryItems.add(PantryItems(foodName, quantity))
//                    }
//                }
//            }
//        }
//        pantryAdapter.updateItems(pantryItems)
//    }
//
//    private fun savePantryItems() {
//        val sharedPreferences =
//            requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val pantryItemsString =
//            pantryAdapter.getPantryItems().joinToString("|") { "${it.name},${it.quantity}" }
//        editor.putString("PantryItems", pantryItemsString)
//        editor.apply()
//    }
//
//    private fun saveSelectedVegetables() {
//        val sharedPreferences =
//            requireActivity().getSharedPreferences("PantryPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val currentItems = sharedPreferences.getString("PantryItems", "") ?: ""
//        val newVegetables = selectedIngredients.joinToString("|") { "$it,1" }
//        val updatedItems =
//            if (currentItems.isNotEmpty()) "$currentItems|$newVegetables" else newVegetables
//        editor.putString("PantryItems", updatedItems)
//        editor.apply()
//    }
//
//    private fun retrieveRecipes(ingredients: List<String>) {
//        val loadingDialog = AlertDialog.Builder(requireContext())
//            .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
//            .setCancelable(false)
//            .create()
//        loadingDialog.show()
//
//        val ingredientsQuery =
//            ingredients.joinToString(",").replace(" ", "%20") // Encode spaces properly
//
//        getRecipesByIngredients(ingredientsQuery).enqueue(object : Callback<RecipesResponse> {
//            override fun onResponse(
//                call: Call<RecipesResponse>,
//                response: Response<RecipesResponse>
//            ) {
//                loadingDialog.dismiss()
//                if (response.isSuccessful) {
//                    val recipes = response.body()?.recipes ?: emptyList()
//
//                    if (recipes.isNotEmpty()) {
//                        // Convert the recipes and start the RecipeDisplay activity
//                        val recipeDetails = recipes.map { recipe ->
//                            RecipeDetailsDisplay(
//                                name = recipe.name,
//                                // Assuming each recipe has a field for ingredients, modify as necessary
//                                ingredients = "", // You might want to format this from the recipe object
//                                instructions = recipe.instructions.toString(),
//                                url = "", // If the API provides a URL for the recipe, replace with the actual field
//                                image = recipe.imageUrl
//                            )
//                        }
//
//                        // Start RecipeDisplay Activity with the fetched recipes
//                        val intent = Intent(requireContext(), RecipeDisplay::class.java).apply {
//                            putParcelableArrayListExtra(
//                                "RECIPES_LIST",
//                                ArrayList(recipeDetails)
//                            )
//                        }
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "No recipes found for the selected ingredients",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    Toast.makeText(
//                        requireContext(),
//                        "Failed to fetch recipes",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
//                loadingDialog.dismiss()
//                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun getRecipesByIngredients(ingredientsQuery: String): Call<RecipesResponse> {
//        val apiService = RetrofitClient.instance
//        return apiService.getRecipesByIngredients(ingredientsQuery)
//    }
//}
//
//private fun Any.getRecipesByIngredients(query: String): Call<RecipesResponse> {
//    TODO("Not yet implemented")
//}
