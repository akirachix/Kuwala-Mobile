
data class RecipesResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val instructions: String?, // Nullable, as instructions might not always be available
    val url: String?, // Nullable, handle cases when url is missing
    val imageUrl: Any
)
