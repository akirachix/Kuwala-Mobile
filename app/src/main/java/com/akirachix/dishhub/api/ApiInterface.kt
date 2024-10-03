package com.akirachix.dishhub.api
import com.akirachix.dishhub.Recipes
import com.akirachix.dishhub.model.RegisterRequest
import com.akirachix.dishhub.model.RegisterResponse
import com.akirachix.dishhub.models.UserProfileResponse
import com.akirachix.dishhub.models.UserProfileUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/users/register/")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>


    @GET("api/user/profile/{id}/")
    fun getUserProfile(@Path("id") userId: Int): Call<UserProfileResponse>


    @PATCH("api/user/profile/update/")
    fun updateUserProfile(@Body userProfile: UserProfileUpdate): Call<Void>


//    @GET("api/recipes/?q=ingredient1&q=ingredient2&q=ingredient3")
//    fun getRecipes( ingredients: List<String>): Call<RecipesResponse>


//    @GET("api/recipes/?q=ingredient1&q=ingredient2&q=ingredient3")
//    fun getRecipes(
//        @Query("ingredient") ingredient: String // Ensure this parameter is annotated properly
//    ): Call<List<Recipe>>

//
//    @GET("recipes")
//    fun getRecipes(
//        @Query("ingredients") ingredients: String // This assumes your API can handle multiple ingredients
//    ): Call<RecipesResponse>



//    @GET("recipes/{id}/information")
//    fun getRecipeInformation(
//        @Path("id") id: Int,
//        @Query("apiKey") apiKey: String = "b2312a182166424a8e94029529542401"
//    ): Call<RecipeInformation>
//
//
//        @GET("recipes/findByIngredients")
//        fun getRecipesByIngredients(
//            @Query("ingredients") ingredients: String,
//            @Query("number") number: Int,
//            @Query("apiKey") apiKey: String
//        ): Call<List<Recipe>>
//
//    fun getRecipesByIngredients(ingredients: String): Call<List<Recipe>>





//    interface ApiService {

//        @GET("recipes/findByIngredients") // Use the actual endpoint path
//        fun getRecipesByIngredients(
//            @Query("ingredients") ingredients: String,
//            s: String
//        ): Call<List<RecipesResponse>> // Ensure the return type matches your expected response

//
//    @GET("recipes/findByIngredients")
//    fun getRecipesByIngredients(
//        @Query("apiKey") apiKey: String?,
//        @Query("ingredients") ingredients: String?
//    ): Call<List<Recipes?>?>?


    @GET("recipes/findByIngredients")
    fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("apiKey") apiKey: String
    ): Call<List<RecipesResponse>>

    @GET("recipes/{id}/information")
    fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = "b2312a182166424a8e94029529542401"
    ): Call<RecipeInformation>

//    }
}













































//    @GET("api/recipes/?q=ingredient1&q=ingredient2&q=ingredient3")
//    fun getRecipes(@Query("q") ingredients: List<String>): Call<RecipesResponse>