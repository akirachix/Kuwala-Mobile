//
//package com.akirachix.dishhub
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.IntentSenderRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.auth.api.identity.BeginSignInRequest
//import com.google.android.gms.auth.api.identity.Identity
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.FirebaseUser
//import android.widget.Button
//
//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var oneTapClient: com.google.android.gms.auth.api.identity.SignInClient
//    private val TAG = "SignUpActivity"
//    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signup)
//
//
//        auth = FirebaseAuth.getInstance()
//        oneTapClient = Identity.getSignInClient(this)
//
//
//        signInLauncher = registerForActivityResult(
//            ActivityResultContracts.StartIntentSenderForResult()
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                handleSignInResult(result.data)
//            } else {
//                Log.e(TAG, "Sign-in failed or cancelled.")
//            }
//        }
//
//
//        val googleSignInButton: Button = findViewById(R.id.google)
//        googleSignInButton.setOnClickListener {
//            signIn()
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
//    private fun signIn() {
//        val signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId(getString(R.string.server_client_id))
//                    .setFilterByAuthorizedAccounts(false)
//                    .build()
//            )
//            .build()
//
//        oneTapClient.beginSignIn(signInRequest)
//            .addOnSuccessListener(this) { result ->
//                try {
//                    signInLauncher.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
//                } catch (e: Exception) {
//                    Log.e(TAG, "Error starting sign-in intent: ${e.message}")
//                }
//            }
//            .addOnFailureListener(this) { e ->
//                Log.e(TAG, "Sign-in failed: ${e.message}")
//            }
//    }
//
//    private fun handleSignInResult(data: Intent?) {
//        try {
//            data?.let {
//                val credential = oneTapClient.getSignInCredentialFromIntent(it)
//                val idToken = credential.googleIdToken
//
//                if (!idToken.isNullOrEmpty()) {
//
//                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                    auth.signInWithCredential(firebaseCredential)
//                        .addOnCompleteListener(this) { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "signInWithCredential:success")
//                                val user = auth.currentUser
//                                updateUI(user)
//
//                                startActivity(Intent(this, Categories::class.java))
//                                finish()
//                            } else {
//                                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                updateUI(null)
//                            }
//                        }
//                } else {
//                    Log.e(TAG, "No ID token found!")
//                }
//            } ?: Log.e(TAG, "Received null data for sign-in.")
//        } catch (e: ApiException) {
//            Log.e(TAG, "Sign-in failed: ${e.message}")
//        }
//    }
//
//    private fun updateUI(user: FirebaseUser?) {
//        if (user != null) {
//            Log.d(TAG, "User signed in: ${user.displayName}")
//        } else {
//            Log.d(TAG, "No user signed in")
//        }
//    }
//
//    private fun signOut() {
//        auth.signOut()
//        Log.d(TAG, "User signed out")
//    }
//}

//package com.akirachix.dishhub
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.akirachix.dishhub.api.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//// Data classes for API request and response
//data class RegisterRequest(
//    val username: String,
//    val password: String,
//    val email: String
//)
//
//data class RegisterResponse(
//    val success: Boolean,
//    val message: String
//)
//
//class Signup : AppCompatActivity() {
//
//    private lateinit var firstNameEditText: EditText
//    private lateinit var lastNameEditText: EditText
//    private lateinit var emailEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var confirmPasswordEditText: EditText
//    private val TAG = "SignUpActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signup)
//
//        // Initialize EditTexts
//        firstNameEditText = findViewById(R.id.etfirstname)
//        lastNameEditText = findViewById(R.id.etLastname)
//        emailEditText = findViewById(R.id.etmail)
//        passwordEditText = findViewById(R.id.etpss)
//        confirmPasswordEditText = findViewById(R.id.etpass)
//
//        val signUpButton: Button = findViewById(R.id.btnlogin)
//        signUpButton.setOnClickListener {
//            registerUser()
//        }
//    }
//
//    private fun registerUser() {
//        val firstName = firstNameEditText.text.toString().trim()
//        val lastName = lastNameEditText.text.toString().trim()
//        val email = emailEditText.text.toString().trim()
//        val password = passwordEditText.text.toString().trim()
//        val confirmPassword = confirmPasswordEditText.text.toString().trim()
//
//        // Basic validation
//        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
//            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (password != confirmPassword) {
//            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Create the request object
//        val registerRequest = RegisterRequest(
//            username = email, // Assuming username is the email
//            password = password,
//            email = email
//        )
//
//        // Call the API
//        RetrofitClient.instance.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
//            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    Log.d(TAG, "Registration successful: ${responseBody?.message}")
//                    Toast.makeText(this@Signup, "Registration successful!", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this@Signup, Login::class.java))
//                    finish()
//                } else {
//                    Log.e(TAG, "Registration failed: ${response.message()}")
//                    Toast.makeText(this@Signup, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                Log.e(TAG, "Error: ${t.message}")
//                Toast.makeText(this@Signup, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//}
//
//private fun <T> Call<T>.enqueue(callback: Callback<RegisterResponse>) {
//
//}

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

class Signup : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
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
