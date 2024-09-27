
package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.api.RetrofitClient
import com.akirachix.dishhub.model.RegisterRequest
import com.akirachix.dishhub.model.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Signup : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize EditTexts
        firstNameEditText = findViewById(R.id.etfirstname)
        lastNameEditText = findViewById(R.id.etLastname)
        emailEditText = findViewById(R.id.etmail)
        passwordEditText = findViewById(R.id.etpss)
        confirmPasswordEditText = findViewById(R.id.etpass)

        val signUpButton: Button = findViewById(R.id.btnlogin)
        signUpButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()


        // Initialize Google OneTap Client
        oneTapClient = Identity.getSignInClient(this)

        // Set up sign-in launcher
        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSignInResult(result.data)
            } else {
                Log.e(TAG, "Sign-in failed or cancelled.")
            }
        }


        val googleSignInButton: Button = findViewById(R.id.google)
        googleSignInButton.setOnClickListener {
            signIn()
        }

            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        // Create a sign-in request for Google ID token
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.server_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        // Start the sign-in flow
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    signInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error starting sign-in intent: ${e.message}")
                }
            }
            .addOnFailureListener(this) { e ->
                Log.e(TAG, "Sign-in failed: ${e.message}")
            }
    }

        if (!validateInputs(firstName, lastName, email, password, confirmPassword)) return

        // Create the request object
        val registerRequest = RegisterRequest(
            username = email, // Assuming username is the email
            password = password,
            email = email
        )

        // Call the API using coroutines
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitClient.instance.registerUser(registerRequest)
                // Access the message from the response body
                Log.d(TAG, "Registration successful: ${response}")
                Toast.makeText(this@Signup, "Registration successful!", Toast.LENGTH_SHORT).show()

                // Start Login Activity after successful registration
                startActivity(Intent(this@Signup, Login::class.java))
                finish()
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
                Toast.makeText(this@Signup, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
                if (!idToken.isNullOrEmpty()) {
      val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "signInWithCredential:success")
                                val user = auth.currentUser
                                updateUI(user)


                                finish()
                            } else {
                                Log.w(TAG, "signInWithCredential:failure", task.exception)
                                updateUI(null)
                            }
                        }
                } else {
                    Log.e(TAG, "No ID token found!")
                }
            } ?: Log.e(TAG, "Received null data for sign-in.")
        } catch (e: ApiException) {
            Log.e(TAG, "Sign-in failed: ${e.message}")
        }
    }

    private fun validateInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return when {
            firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                false
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun handleErrorResponse(response: Response<RegisterResponse>) {
        Log.e(TAG, "Registration failed: ${response.message()}")
        val errorMessage = when (response.code()) {
            409 -> "Username or email already exists."
            400 -> "Bad request. Please check your input."
            else -> "Registration failed: ${response.message()}"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
