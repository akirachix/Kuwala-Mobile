import com.akirachix.dishhub.api.EdamamApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory









//package com.akirachix.dishhub.api
//
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//object RetrofitClientEdamam {
//    private const val BASE_URL = "https://api.edamam.com/"
//
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val original = chain.request()
//            val originalHttpUrl = original.url
//
//            val url = originalHttpUrl.newBuilder()
//                .build()
//
//            val request = original.newBuilder()
//                .url(url)
//                .build()
//
//            chain.proceed(request)
//        }
//        .build()
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: EdamamApiService by lazy {
//        retrofit.create(EdamamApiService::class.java)
//    }
//}
//
//interface EdamamApiService {
//    @GET("search")
//    fun getRecipesByIngredients(
//        @Query("q") ingredients: String,
//        @Query("app_id") appId: String,
//        @Query("app_key") appKey: String
//    ): retrofit2.Call<EdamamApiResponse>
//}





//package com.akirachix.dishhub.api
//
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//object RetrofitClientEdamam {
//    private const val BASE_URL = "https://api.edamam.com/"
//
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val original = chain.request()
//            val originalHttpUrl = original.url
//
//            val url = originalHttpUrl.newBuilder()
//                .build()
//
//            val request = original.newBuilder()
//                .url(url)
//                .build()
//
//            chain.proceed(request)
//        }
//        .build()
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: EdamamApiService by lazy {
//        retrofit.create(EdamamApiService::class.java)
//    }
//}
//
//interface EdamamApiService {
//    @GET("search")
//    fun getRecipesByIngredients(
//        @Query("q") ingredients: String,
//        @Query("app_id") appId: String,
//        @Query("app_key") appKey: String
//    ): retrofit2.Call<EdamamApiResponse>
//}






import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitClientEdamam {
    private const val BASE_URL = "https://api.edamam.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .build()

            val request = original.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: EdamamApiService by lazy {
        retrofit.create(EdamamApiService::class.java)
    }
}

interface EdamamApiService {
    @GET("search")
    fun getRecipesByIngredients(
        @Query("q") ingredients: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String
    ): retrofit2.Call<EdamamApiResponse>
}

// EdamamApiResponse.kt









//
//object RetrofitClient {
//    private const val BASE_URL = "https://api.yummy.com/v1/"
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: YummyApiService by lazy {
//        retrofit.create(YummyApiService::class.java)
//    }
//
//    class YummyApiService {
//
//    }
//}