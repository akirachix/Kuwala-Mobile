plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
// Add the kapt plugin for Kotlin
}

android {
    namespace = "com.akirachix.dishhub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.akirachix.dishhub"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)  // Core KTX
    implementation(libs.androidx.appcompat)  // AppCompat
    implementation(libs.material)  // Material Components
    implementation(libs.androidx.activity)  // Activity KTX
    implementation(libs.androidx.constraintlayout)  // ConstraintLayout

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))  // Firebase BOM for version management
    implementation(libs.firebase.auth)  // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation ("com.google.firebase:firebase-auth-ktx:21.0.3")
    implementation ("com.google.android.gms:play-services-auth:20.4.0")

    // Google Play Services
    implementation(libs.play.services.auth)  // Play Services Auth

    // AndroidX lifecycle components
    implementation(libs.androidx.lifecycle.livedata.ktx)  // LiveData KTX
    implementation(libs.androidx.lifecycle.viewmodel.ktx)  // ViewModel KTX
    implementation(libs.androidx.fragment.ktx)  // Fragment KTX

    // Navigation components
    implementation(libs.androidx.navigation.fragment.ktx)  // Navigation Fragment KTX
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.filament.android)
    implementation(libs.mediation.test.suite)
    implementation(libs.androidx.foundation.layout.android)  // Navigation UI KTX

    // Testing dependencies
    testImplementation(libs.junit)  // Unit tests
    androidTestImplementation(libs.androidx.junit)  // AndroidX JUnit tests
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI tests

    // Retrofit and OkHttp
    implementation(libs.retrofit.v2110)
    implementation(libs.converter.gson.v2110)
    implementation(libs.logging.interceptor.v4100)
    implementation ("androidx.recyclerview:recyclerview:1.3.0")
    // Glide for image loading
    implementation(libs.glide)
    kapt("com.github.bumptech.glide:compiler:4.12.0")  // Enable annotation processor

    // Fragment KTX
    val fragmentVersion = "1.5.7"
    implementation(libs.androidx.fragment.ktx.v157)
    implementation (libs.okhttp)
    implementation (libs.gson)// Gson for JSON parsing


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation( "com.google.code.gson:gson:2.8.9")
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.google.code.gson:gson:2.10.1")



}




