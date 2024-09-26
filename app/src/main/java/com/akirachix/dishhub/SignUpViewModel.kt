package com.akirachix.dishhub.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signUpResult = MutableLiveData<Result<Unit>>()
    val signUpResult: LiveData<Result<Unit>> = _signUpResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Implement your sign up logic here
                // For example, call an API or insert into a local database
                // If successful:
                _signUpResult.value = Result.success(Unit)
            } catch (e: Exception) {
                _signUpResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}