package com.akirachix.dishhub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.dishhub.Categories
import com.akirachix.dishhub.ViewModel.SignInViewModel
import com.akirachix.dishhub.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        signInViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess {
                navigateToCategories()
            }.onFailure { throwable ->
                Log.e("Login", "Login failed: ${throwable.localizedMessage}")
                showError(throwable.localizedMessage ?: "Login failed.")
            }
        })
    }

    private fun setupClickListeners() {
        binding.txtsignUp.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        binding.btnLog.setOnClickListener {
            handleEmailLogin() // Call this method to perform login first
        }
    }

    private fun handleEmailLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etpss.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter valid username and password")
            return
        }

        binding.btnLog.isEnabled = false
        signInViewModel.login(username, password)
    }

    private fun navigateToCategories() {
        startActivity(Intent(this, Categories::class.java))
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        binding.btnLog.isEnabled = true
    }
}
