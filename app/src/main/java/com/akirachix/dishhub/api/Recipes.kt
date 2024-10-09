package com.akirachix.dishhub

data class Recipe<Ingredient>(
    val id: Int,
    val title: String,
    val image: String,  // Image URL of the recipe
    val readyInMinutes: Int,  // Time to make the recipe
    val servings: Int,  // Number of servings
    val ingredients: List<Ingredient>,  // List of ingredients needed for the recipe
    val instructions: String  // Full recipe instructions
)

