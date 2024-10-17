

package com.akirachix.dishhub.api

data class EdamamApiResponse(
    val hits: List<Hit>
)

data class Hit(
    val recipe: Recipe
)

