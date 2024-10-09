package com.akirachix.dishhub

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_EMAIL = "email"
        private const val KEY_USER_ID = "user_id" // Added key for user ID
    }

    fun saveLoginSession(email: String, userId: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_USER_ID, userId) // Saving user ID
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null) // Retrieve user ID
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }




}