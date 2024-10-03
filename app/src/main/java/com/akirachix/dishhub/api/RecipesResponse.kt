package com.akirachix.dishhub.api


//        return TODO("Provide the return value")


data class RecipesResponse(
    val id: Int,
    val title: String,
    val usedIngredients: List<Ingredient>,
    val missedIngredients: List<Ingredient>
) {
}

data class Ingredient(
    val id: Int,
    val name: String,
    // Add other fields as needed
)

data class RecipeInformation(
    val instructions: String?,
    val name: Unit
    // Add other fields as needed
)
