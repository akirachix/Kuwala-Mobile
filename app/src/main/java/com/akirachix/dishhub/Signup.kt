





package com.akirachix.dishhub


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var sharedPreferences: SharedPreferences


    private val MIN_PASSWORD_LENGTH = 6


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)


        initializeViews()
        setupClickListeners()
    }


    private fun initializeViews() {
        etFirstName = findViewById(R.id.etfirstname)
        etLastName = findViewById(R.id.etLastname)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etpss)
        etConfirmPassword = findViewById(R.id.etpass)
        btnSignUp = findViewById(R.id.btnlogin)
    }


    private fun setupClickListeners() {
        btnSignUp.setOnClickListener {
            performSignUp()
        }


        findViewById<TextView>(R.id.txtlogin).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }


    private fun performSignUp() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()


        if (!validateInputs(firstName, lastName, email, password, confirmPassword)) {
            return
        }


        // Check if email already exists
        if (isEmailRegistered(email)) {
            showToast("This email is already registered")
            return
        }


        // Save user data
        saveUserData(firstName, lastName, email, password)


        showToast("Registration successful!")


        // Navigate to Login
        startActivity(Intent(this, Login::class.java))
        finish()
    }


    private fun validateInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        when {
            firstName.isEmpty() -> {
                etFirstName.error = "First name is required"
                etFirstName.requestFocus()
                return false
            }
            lastName.isEmpty() -> {
                etLastName.error = "Last name is required"
                etLastName.requestFocus()
                return false
            }
            email.isEmpty() -> {
                etEmail.error = "Email is required"
                etEmail.requestFocus()
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Please enter a valid email address"
                etEmail.requestFocus()
                return false
            }
            password.isEmpty() -> {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return false
            }
            password.length < MIN_PASSWORD_LENGTH -> {
                etPassword.error = "Password must be at least $MIN_PASSWORD_LENGTH characters"
                etPassword.requestFocus()
                return false
            }
            confirmPassword.isEmpty() -> {
                etConfirmPassword.error = "Please confirm your password"
                etConfirmPassword.requestFocus()
                return false
            }
            password != confirmPassword -> {
                etConfirmPassword.error = "Passwords do not match"
                etConfirmPassword.requestFocus()
                return false
            }
        }
        return true
    }


    private fun isEmailRegistered(email: String): Boolean {
        val registeredEmails = sharedPreferences.getStringSet("registered_emails", setOf()) ?: setOf()
        return registeredEmails.contains(email)
    }


    private fun saveUserData(firstName: String, lastName: String, email: String, password: String) {
        val editor = sharedPreferences.edit()


        // Add email to registered emails set
        val registeredEmails = sharedPreferences.getStringSet("registered_emails", setOf())?.toMutableSet() ?: mutableSetOf()
        registeredEmails.add(email)
        editor.putStringSet("registered_emails", registeredEmails)


        // Save user details
        editor.putString("${email}_firstName", firstName)
        editor.putString("${email}_lastName", lastName)
        editor.putString("${email}_password", password) // Note: In a real app, you should hash the password
        editor.putString("${email}_fullName", "$firstName $lastName")


        // Save current user
        editor.putString("current_user_email", email)


        editor.apply()
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


