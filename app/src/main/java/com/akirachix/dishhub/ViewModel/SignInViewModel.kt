//////package com.akirachix.dishhub.ViewModel
//////
//////import android.app.Application
//////import androidx.lifecycle.AndroidViewModel
//////import androidx.lifecycle.LiveData
//////import androidx.lifecycle.MutableLiveData
//////import com.akirachix.dishhub.model.LoginRequest
//////
//////class SignInViewModel(application: Application) : AndroidViewModel(application) {
//////
//////    val isLoading: Any
//////        get() {
//////            TODO()
//////        }
//////    private val _loginResult = MutableLiveData<Result<String>>()
//////    val loginResult: LiveData<Result<String>> = _loginResult
//////
//////    private val _googleSignInResult = MutableLiveData<Result<String>>()
//////    val googleSignInResult: LiveData<Result<String>> = _googleSignInResult
//////
//////    fun login(username: String, password: String) {
//////        if (!validateForm(username, password)) return
//////
//////        val loginRequest = LoginRequest(username, password)
//////
//////
//////    }
//////
//////    fun validateForm(username: String, password: String): Boolean {
//////        return when {
//////            username.isEmpty() -> {
//////                _loginResult.postValue(Result.failure(Throwable("Username is required")))
//////                false
//////            }
//////            password.isEmpty() -> {
//////                _loginResult.postValue(Result.failure(Throwable("Password is required")))
//////                false
//////            }
//////            password.length < 6 -> {
//////                _loginResult.postValue(Result.failure(Throwable("Password must be at least 6 characters long")))
//////                false
//////            }
//////            else -> true
//////        }
//////    }
//////}
//////
////
//////package com.akirachix.dishhub.ViewModel
//////
//////import android.app.Application
//////import androidx.lifecycle.AndroidViewModel
//////import androidx.lifecycle.LiveData
//////import androidx.lifecycle.MutableLiveData
//////import androidx.lifecycle.viewModelScope
//////import com.akirachix.dishhub.api.ApiClient
//////import com.akirachix.dishhub.api.ApiInterface
//////import com.akirachix.dishhub.model.LoginRequest
//////import kotlinx.coroutines.launch
//////import retrofit2.HttpException
//////
//////class SignInViewModel(application: Application) : AndroidViewModel(application) {
//////    private val _loginResult = MutableLiveData<Result<String>>()
//////    val loginResult: LiveData<Result<String>> = _loginResult
//////
//////    private val apiService = ApiClient.retrofit.create(ApiInterface::class.java)
//////
//////    fun login(username: String, password: String) {
//////        if (!validateForm(username, password)) return
//////
//////        val loginRequest = LoginRequest(username, password)
//////
//////        viewModelScope.launch {
//////            try {
//////                val response = apiService.login(loginRequest)
//////                if (response.isSuccessful) {
//////                    val body = response.body()
//////                    if (body != null && body.status == "success") {
//////                        _loginResult.postValue(Result.success(body.message))
//////                    } else {
//////                        _loginResult.postValue(Result.failure(Throwable(body?.message ?: "Login failed")))
//////                    }
//////                } else {
//////                    _loginResult.postValue(Result.failure(Throwable("Error: ${response.code()} ${response.message()}")))
//////                }
//////            } catch (e: HttpException) {
//////                _loginResult.postValue(Result.failure(Throwable("Network error: ${e.message()}")))
//////            } catch (e: Exception) {
//////                _loginResult.postValue(Result.failure(Throwable("An error occurred: ${e.localizedMessage}")))
//////            }
//////        }
//////    }
//////
//////    fun validateForm(username: String, password: String): Boolean {
//////        return when {
//////            username.isEmpty() -> {
//////                _loginResult.postValue(Result.failure(Throwable("Username is required")))
//////                false
//////            }
//////            password.isEmpty() -> {
//////                _loginResult.postValue(Result.failure(Throwable("Password is required")))
//////                false
//////            }
//////            password.length < 6 -> {
//////                _loginResult.postValue(Result.failure(Throwable("Password must be at least 6 characters long")))
//////                false
//////            }
//////            else -> true
//////        }
//////    }
//////}
////
////
////package com.akirachix.dishhub.ViewModel
////
////import android.app.Application
////import androidx.lifecycle.AndroidViewModel
////import androidx.lifecycle.LiveData
////import androidx.lifecycle.MutableLiveData
////import androidx.lifecycle.viewModelScope
////import com.akirachix.dishhub.api.ApiClient
////import com.akirachix.dishhub.api.ApiInterface
////import com.akirachix.dishhub.model.LoginRequest
////import kotlinx.coroutines.launch
////import retrofit2.HttpException
////
////class SignInViewModel(application: Application) : AndroidViewModel(application) {
////
////    private val _loginResult = MutableLiveData<Result<String>>()
////    val loginResult: LiveData<Result<String>> = _loginResult
////
////    private val apiService = ApiClient.retrofit.create(ApiInterface::class.java)
////
////    // API Login Logic with Coroutines
////    fun login(username: String, password: String) {
////        if (!validateForm(username, password)) return
////
////        val loginRequest = LoginRequest(username, password)
////
////        viewModelScope.launch {
////            try {
////                val response = apiService.login(loginRequest)
////                if (response.isSuccessful) {
////                    val body = response.body()
////                    if (body != null && body.status == "success") {
////                        _loginResult.postValue(Result.success(body.message))
////                    } else {
////                        _loginResult.postValue(Result.failure(Throwable(body?.message ?: "Login failed")))
////                    }
////                } else {
////                    _loginResult.postValue(Result.failure(Throwable("Error: ${response.code()} ${response.message()}")))
////                }
////            } catch (e: HttpException) {
////                _loginResult.postValue(Result.failure(Throwable("Network error: ${e.message()}")))
////            } catch (e: Exception) {
////                _loginResult.postValue(Result.failure(Throwable("An error occurred: ${e.localizedMessage}")))
////            }
////        }
////    }
////
////    // Validation Logic
////    fun validateForm(username: String, password: String): Boolean {
////        return when {
////            username.isEmpty() -> {
////                _loginResult.postValue(Result.failure(Throwable("Username is required")))
////                false
////            }
////            password.isEmpty() -> {
////                _loginResult.postValue(Result.failure(Throwable("Password is required")))
////                false
////            }
////            password.length < 6 -> {
////                _loginResult.postValue(Result.failure(Throwable("Password must be at least 6 characters long")))
////                false
////            }
////            else -> true
////        }
////    }
////}
//
//
//package com.akirachix.dishhub.ViewModel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.akirachix.dishhub.api.ApiClient
//import com.akirachix.dishhub.api.ApiInterface
//import com.akirachix.dishhub.model.LoginRequest
//import com.akirachix.dishhub.model.LoginResponse
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//
//class SignInViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val _loginResult = MutableLiveData<Result<String>>()
//    val loginResult: LiveData<Result<String>> = _loginResult
//
//    private val apiService = ApiClient.retrofit.create(ApiInterface::class.java)
//
//    // API Login Logic with Coroutines
//    fun login(username: String, password: String) {
//        if (!validateForm(username, password)) return
//
//        val loginRequest = LoginRequest(username, password)
//
//        viewModelScope.launch {
//            try {
//                val response = apiService.login(loginRequest)
//                handleResponse(response)
//            } catch (e: HttpException) {
//                _loginResult.postValue(Result.failure(Throwable("Network error: ${e.message()}")))
//            } catch (e: Exception) {
//                _loginResult.postValue(Result.failure(Throwable("An error occurred: ${e.localizedMessage}")))
//            }
//        }
//    }
//
//    // Handle API response
//    private fun handleResponse(response: retrofit2.Response<LoginResponse>) {
//        if (response.isSuccessful) {
//            val body = response.body()
//            if (body != null && body.status == "success") {
//                _loginResult.postValue(Result.success(body.message))
//            } else {
//                _loginResult.postValue(Result.failure(Throwable(body?.message ?: "Login failed")))
//            }
//        } else {
//            _loginResult.postValue(Result.failure(Throwable("Error: ${response.code()} ${response.message()}")))
//        }
//    }
//
//    // Validation Logic
//    private fun validateForm(username: String, password: String): Boolean {
//        return when {
//            username.isEmpty() -> {
//                _loginResult.postValue(Result.failure(Throwable("Username is required")))
//                false
//            }
//            password.isEmpty() -> {
//                _loginResult.postValue(Result.failure(Throwable("Password is required")))
//                false
//            }
//            password.length < 6 -> {
//                _loginResult.postValue(Result.failure(Throwable("Password must be at least 6 characters long")))
//                false
//            }
//            else -> true
//        }
//    }
//}

package com.akirachix.dishhub.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akirachix.dishhub.api.ApiClient
import com.akirachix.dishhub.api.ApiInterface
import com.akirachix.dishhub.model.LoginRequest
import com.akirachix.dishhub.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val apiService = ApiClient.retrofit.create(ApiInterface::class.java)

    // API Login Logic with Coroutines
    fun login(username: String, password: String) {
        if (!validateForm(username, password)) return

        val loginRequest = LoginRequest(username, password)

        viewModelScope.launch {
            try {
                val response = apiService.login(loginRequest)
                Log.d("LoginResponse", response.raw().toString()) // Log the raw response
                handleResponse(response)
            } catch (e: HttpException) {
                // Handle exceptions
            }
        }
    }

    private fun handleResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (body.status == "success") {
                    _loginResult.postValue(Result.success(body.message))
                } else {
                    _loginResult.postValue(Result.failure(Throwable(body.message)))
                }
            } else {
                _loginResult.postValue(Result.failure(Throwable("Empty response body")))
            }
        } else {
            _loginResult.postValue(Result.failure(Throwable("Error: ${response.code()} ${response.message()}")))
        }
    }



    private fun validateForm(username: String, password: String): Boolean {
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
