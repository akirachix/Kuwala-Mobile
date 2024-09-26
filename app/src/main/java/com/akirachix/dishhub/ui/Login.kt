
package com.akirachix.dishhub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.dishhub.Categories
import com.akirachix.dishhub.HomeFragment
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
        // Observe login result from ViewModel
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
            startActivity(Intent(this, HomeFragment::class.java))
        }

        binding.btnLog.setOnClickListener {
            startActivity(Intent(this, Categories::class.java))

        }
    }

    private fun handleEmailLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etpss.text.toString().trim()

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter valid username and password")
            return
        }

        binding.btnLog.isEnabled = false

        signInViewModel.login(username, password)
    }

    private fun navigateToCategories() {
        startActivity(Intent(this, Categories::class.java))
        finish() // Close login activity so it doesn't remain in the back stack
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // Re-enable the login button after showing the error
        binding.btnLog.isEnabled = true
    }
}

