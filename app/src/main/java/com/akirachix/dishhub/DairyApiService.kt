//import com.akirachix.dishhub.Dairy
//import com.akirachix.dishhub.api.RecipesResponse
//
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//
//interface ApiService {
//    @GET("api/categories/4/food-items/")
//    fun getFruits(): Call<List<Dairy>>
//
//    // Define the recipe fetching method
//    @GET("api/recipes") // Adjust the endpoint based on your actual API path
//    fun getRecipes(@Query("ingredients") ingredientsQuery: String): Call<List<RecipesResponse>>
//    fun getRecipesByIngredients(ingredientsQuery: String, appId: String, appKey: String): Any {
//
//
//        return TODO("Provide the return value")
//    }
//
//}


import com.akirachix.dishhub.Dairy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//interface ApiService {
//    @GET("api/categories/4/food-items/")
//    fun getFruits(): Call<List<Dairy>>
//
//    // Define the recipe fetching method
//    @GET("api/recipes") // Adjust the endpoint based on your actual API path
//    fun getRecipes(@Query("ingredients") ingredientsQuery: String): Call<List<RecipesResponse>>
//    fun getRecipesByIngredients(ingredientsQuery: String, appId: String, appKey: String): Any {
//
//
//        return
//    }
//
//}



//interface ApiService {
//
//    @GET("api/categories/4/food-items/")
//    fun getFruits(): Call<List<Dairy>>
//
//    // Fetch recipes based on ingredients
//    @GET("api/recipes") // Adjust this to your actual endpoint
//    fun getRecipes(
//        @Query("ingredients") ingredientsQuery: String
//    ): Call<List<RecipesResponse>>
//
//    // Fetch recipes with appId and appKey included
//    @GET("api/recipes") // Adjust this to your actual endpoint
//    fun getRecipesByIngredients(
//        @Query("ingredients") ingredientsQuery: String,
//        @Query("app_id") appId: String,
//        @Query("app_key") appKey: String
//    ): Call<List<RecipesResponse>>  // Return Call<List<RecipesResponse>> to handle the API call
//}





interface ApiService {
    @GET("api/categories/4/food-items/")  // Replace with the correct endpoint for Dairy items
    fun getDairyItems(): Call<List<Dairy>>


    @GET("api/recipes")
    fun getRecipes(@Query("ingredients") ingredientsQuery: String): Call<List<RecipesResponse>>
}
