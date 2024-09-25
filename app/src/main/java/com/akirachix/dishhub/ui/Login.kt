package com.akirachix.dishhub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.dishhub.ViewModel.SignInViewModel
import com.akirachix.dishhub.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signInViewModel: SignInViewModel by viewModels()

        binding.txtsignUp.setOnClickListener() {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        binding.btnLog.setOnClickListener {
            handleEmailLogin(signInViewModel)
        }

        signInViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess {
                navigateToMain()
            }.onFailure { throwable ->
                showError(throwable.localizedMessage ?: "Login failed.")
            }
        })
    }

    private fun handleEmailLogin(signInViewModel: SignInViewModel) {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etpss.text.toString().trim()

        if (signInViewModel.validateForm(username, password)) {
            signInViewModel.login(username, password)
        } else {
            Toast.makeText(this, "Please enter valid username and password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, Categories::class.java)
        startActivity(intent)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

//



//package com.akirachix.dishhub
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.akirachix.dishhub.databinding.ActivityLoginBinding
//import com.akirachix.dishhub.databinding.ActivitySignupBinding
//import com.akirachix.dishhub.ui.Categories
//import com.akirachix.dishhub.ui.Signup
//
//class Login : AppCompatActivity() {
//    lateinit var binding: ActivityLoginBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnLog.setOnClickListener {
//            val intent = Intent(this, Signup::class.java)
//            startActivity(intent)
//        }
//        binding.btnLog.setOnClickListener {
//            val intent = Intent(this, Categories::class.java)
//            startActivity(intent)
//        }
//    }
//}