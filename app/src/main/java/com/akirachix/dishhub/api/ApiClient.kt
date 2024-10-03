package com.akirachix.dishhub.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//
//package com.akirachix.dishhub.api
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClient {
//    private const val BASE_URL = "https://www.themealdb.com/"
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}



//package com.akirachix.dishhub.api
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClient {
//    private const val BASE_URL = "https://www.themealdb.com/"
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}



//
//package com.akirachix.dishhub.api
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClientSpoonacular {
//    private const val BASE_URL = "https://api.spoonacular.com/"
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}




//
//object RetrofitClientSpoonacular {
//    private const val BASE_URL = "https://api.spoonacular.com/"
//    private const val API_KEY = "b2312a182166424a8e94029529542401"
//
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val original = chain.request()
//            val originalHttpUrl = original.url
//
//            val url = originalHttpUrl.newBuilder()
//                .addQueryParameter("b2312a182166424a8e94029529542401", API_KEY)
//                .build()
//
//            val requestBuilder = original.newBuilder()
//                .url(url)
//
//            val request = requestBuilder.build()
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
//    val instance: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}



object RetrofitClientSpoonacular {
    private const val BASE_URL = "https://api.spoonacular.com/"
    private const val API_KEY = "b2312a182166424a8e94029529542401"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            // Don't add API key here since we're adding it in the requests
            val request = original.newBuilder()
                .url(originalHttpUrl)
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

    val instance: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}









//
//object RetrofitClient {
//    private const val BASE_URL = "https://dishhub-2ea9d6ca8e11.herokuapp.com/"
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val instance: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}