//package com.akirachix.dishhub
//
//data class RecipesResponse(
//    val recipes: List<Recipe<Any?>>,  // List of Recipe objects
//    val title: String,
//    val missedIngredients: Any,
//    val usedIngredients: Any,
//    val id: Int = 0,
//    val image: String = ""
//) {





//}




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




//data class RecipesResponse(
//    val hits: List<RecipeHit>
//)
//
//data class RecipeHit(
//    val recipe: Recipe
//)
//
//data class Recipe(
//    val label: String,
//    val ingredientLines: ArrayList<String>?,
//    val url: String?,                  // URL to the recipe
//    val image: String?,                // Image URL for the recipe
//    val ingredientCount: Int?          // Number of ingredients
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString().toString(),
//        parcel.createStringArrayList(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readValue(Int::class.java.classLoader) as? Int
//    ) {
//    }
//
//    override fun describeContents(): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeToParcel(p0: Parcel, p1: Int) {
//        TODO("Not yet implemented")
//    }
//
//    companion object CREATOR : Parcelable.Creator<Recipe> {
//        override fun createFromParcel(parcel: Parcel): Recipe {
//            return Recipe(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Recipe?> {
//            return arrayOfNulls(size)
//        }
//    }
//}









//
//data class RecipesResponse(
//    val hits: List<RecipeHit>,
//    val count: Int,
//    val from: Int,
//    val to: Int
//)
//
//data class RecipeHit(
//    val recipe: Recipe
//)
//
//data class Recipe(
//    val uri: String,
//    val label: String,
//    val image: String?,
//    val source: String,
//    val url: String,
//    val yield: Double,
//    val calories: Double,
//    val totalTime: Double,
//    val ingredientLines: List<String>
//)