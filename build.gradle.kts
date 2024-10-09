
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()  // You might want to add this for Google's services
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}
