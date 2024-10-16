





package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SessionManager
        sessionManager = SessionManager(this)

        // Check if the user is already logged in
        if (sessionManager.isLoggedIn()) {
            // User is logged in, redirect to Categories
            startActivity(Intent(this, Categories::class.java))
            finish() // Close the Login activity
            return
        }

        setupTextWatchers()

        binding.txtsignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnlogin.setOnClickListener {
            if (validateInputs()) {
                handleLogin()
            }
        }
    }

    private fun setupTextWatchers() {
        binding.etEmail.addTextChangedListener(createTextWatcher(binding.tilEmail))
        binding.etpss.addTextChangedListener(createTextWatcher(binding.tilPassword))
    }

    private fun createTextWatcher(textInputLayout: TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun validateInputs(): Boolean {
        clearErrors()
        var formError = false

        val email = binding.etEmail.text.toString()
        if (email.isBlank()) {
            formError = true
            binding.tilEmail.error = "Email is required"
        }

        val password = binding.etpss.text.toString()
        if (password.isBlank()) {
            formError = true
            binding.tilPassword.error = "Password is required"
        }

        return !formError
    }

    private fun clearErrors() {
        binding.tilEmail.error = null
        binding.tilPassword.error = null
    }

    private fun handleLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etpss.text.toString()

        // Assume you have a method to validate credentials that returns user details
        val userId = validateCredentials(email, password)

        if (userId != null) {
            sessionManager.saveLoginSession(email, userId) // Save email and user ID
            startActivity(Intent(this, Categories::class.java))
            finish() // Close the Login activity
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }

    // Replace this stub method with actual user validation logic
    private fun validateCredentials(email: String, password: String): String? {
        // Here you should implement your actual authentication logic
        return "someUserId" // Example user ID
    }
}