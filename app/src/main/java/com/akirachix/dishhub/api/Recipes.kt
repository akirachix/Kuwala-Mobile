



package com.akirachix.dishhub.api



data class Recipe(
    val label: String, // Recipe title
    val ingredientLines: List<String>, // Full list of ingredients
    val instructions: String?, // Instructions if available (you might need a workaround for this)
    val url: String, // You can choose to ignore this if you don’t need the URL
    val image: String? // Image URL
)


