package com.akirachix.dishhub
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputLayout


class Signup: AppCompatActivity() {


    lateinit var binding: ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtlogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        setupTextWatchers()
    }


    private fun setupTextWatchers() {
        binding.etfirstname.addTextChangedListener(createTextWatcher(binding.tilfirstname))
        binding.etLastname.addTextChangedListener(createTextWatcher(binding.tilLastName))
        binding.etmail.addTextChangedListener(createTextWatcher(binding.tilEmail))
        binding.etpass.addTextChangedListener(createTextWatcher(binding.tilPassword))
        binding.etpass.addTextChangedListener(createTextWatcher(binding.tilpass))
    }


    private fun createTextWatcher(textInputLayout: TextInputLayout): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textInputLayout.error = null
        }


        override fun afterTextChanged(s: Editable?) {}
    }


    private fun validateRegistration(): Boolean {
        clearErrors()
        var formError = false


        val firstName = binding.etfirstname.text.toString()
        if (firstName.isBlank()) {
            formError = true
            binding.tilfirstname.error = "First name is required"
        } else if (!valid(firstName)) {
            formError = true
            binding.tilfirstname.error = "First name must contain letters only"
        }



        val lastName = binding.etLastname.text.toString()
        if (lastName.isBlank()) {
            formError = true
            binding.tilLastName.error = "Last name is required"
        } else if (!valid(lastName)) {
            formError = true
            binding.tilLastName.error = "Last name must contain letters only"
        }



        val email = binding.etmail.text.toString()
        if (email.isBlank()) {
            formError = true
            binding.tilEmail.error = "Email is required"
        } else if (!email.contains("@")) {
            formError = true
            binding.tilEmail.error = "Email must contain an @ character"
        }



        val passWord = binding.etpass.text.toString()
        if (passWord.isBlank()) {
            formError = true
            binding.tilPassword.error = "Password is required"
        } else if (!match(passWord)) {
            formError = true
            binding.tilPassword.error = "Password must contain at least one special character (@ or *) and one number (0-9)"
        } else if (passWord.length != 8) {
            formError = true
            binding.tilPassword.error = "Password must be 8 characters long"
        }


        val confirm = binding.etpass.text.toString()
        if (confirm != passWord) {
            formError = true
            binding.tilpass.error = "Passwords do not match"
        }


        return !formError
    }


    private fun clearErrors() {
        binding.tilfirstname.error = null
        binding.tilLastName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilpass.error = null
    }


    private fun valid(name: String): Boolean {
        val nameRegex = Regex("[a-zA-Z\\s]+$")
        return nameRegex.matches(name)
    }


    private fun match(password: String): Boolean {
        val specialChar = password.matches(Regex(".*[@*].*"))
        val hasDigit = password.matches(Regex(".*\\d.*"))
        return specialChar && hasDigit
    }
}




