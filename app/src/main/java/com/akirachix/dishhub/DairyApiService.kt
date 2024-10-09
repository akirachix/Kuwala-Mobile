import com.akirachix.dishhub.Dairy
import com.akirachix.dishhub.api.RecipesResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("api/categories/4/food-items/")
    fun getFruits(): Call<List<Dairy>>

    // Define the recipe fetching method
    @GET("api/recipes") // Adjust the endpoint based on your actual API path
    fun getRecipes(@Query("ingredients") ingredientsQuery: String): Call<List<RecipesResponse>>

}