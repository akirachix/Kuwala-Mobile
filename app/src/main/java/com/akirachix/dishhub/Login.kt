package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatchers()

        binding.txtsignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.btnlogin.setOnClickListener {
            if (validateInputs()) {
                // Proceed to the next activity (e.g., Categories)
                startActivity(Intent(this, Categories::class.java))
            }
        }
    }

    private fun setupTextWatchers() {
        binding.etmail.addTextChangedListener(createTextWatcher(binding.tilEmail))
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

        val email = binding.etmail.text.toString()
        if (email.isBlank()) {
            formError = true
            binding.tilEmail.error = "Email is required"
        } else if (!isValidEmail(email)) {
            formError = true
            binding.tilEmail.error = "Email must contain an @ character"
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

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }
}
