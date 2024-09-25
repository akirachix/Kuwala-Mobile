package com.akirachix.dishhub.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akirachix.dishhub.model.LoginRequest

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _googleSignInResult = MutableLiveData<Result<String>>()
    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult

    fun login(username: String, password: String) {
        if (!validateForm(username, password)) return

        val loginRequest = LoginRequest(username, password)


    }

    fun validateForm(username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                _loginResult.postValue(Result.failure(Throwable("Username is required")))
                false
            }
            password.isEmpty() -> {
                _loginResult.postValue(Result.failure(Throwable("Password is required")))
                false
            }
            password.length < 6 -> {
                _loginResult.postValue(Result.failure(Throwable("Password must be at least 6 characters long")))
                false
            }
            else -> true
        }
    }
}

