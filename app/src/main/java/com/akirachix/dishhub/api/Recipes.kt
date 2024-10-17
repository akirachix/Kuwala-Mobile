//package com.akirachix.dishhub
//
//data class Recipe<Ingredient>(
//    val id: Int,
//    val title: String,
//    val image: String,  // Image URL of the recipe
//    val readyInMinutes: Int,  // Time to make the recipe
//    val servings: Int,  // Number of servings
//    val ingredients: List<Ingredient>,  // List of ingredients needed for the recipe
//    val instructions: String  // Full recipe instructions
//)



package com.akirachix.dishhub.api

//data class Recipe(
//    val label: String,
//    val ingredients: List<RecipeIngredient>,  // Renamed to RecipeIngredient
//    val instructions: String,
//    val url: String
//)
//
//data class RecipeIngredient(  // Renamed class to avoid conflict
//    val text: String
//)


data class Recipe(
    val label: String, // Recipe title
    val ingredientLines: List<String>, // Full list of ingredients
    val instructions: String?, // Instructions if available (you might need a workaround for this)
    val url: String, // You can choose to ignore this if you don’t need the URL
    val image: String? // Image URL
)


