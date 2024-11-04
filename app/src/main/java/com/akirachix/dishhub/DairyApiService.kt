


import com.akirachix.dishhub.Dairy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query





interface ApiService {
    @GET("api/categories/4/food-items/")  // Replace with the correct endpoint for Dairy items
    fun getDairyItems(): Call<List<Dairy>>


    @GET("api/recipes")
    fun getRecipes(@Query("ingredients") ingredientsQuery: String): Call<List<RecipesResponse>>
    fun getPantryItems(): Any {
        TODO("Not yet implemented")
    }
}
