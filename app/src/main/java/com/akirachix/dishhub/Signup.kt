//
package com.akirachix.dishhub
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
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
//        auth = FirebaseAuth.getInstance()
//        oneTapClient = Identity.getSignInClient(this)
//
//        signInLauncher = registerForActivityResult(
//            ActivityResultContracts.StartIntentSenderForResult()
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                handleSignInResult(result.data)
//            } else {
//                Log.e(TAG, "Sign-in failed or cancelled.")
//                Toast.makeText(this, "Sign-in failed or cancelled.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        // Handle Google sign-in button click
//        val googleSignInButton: Button = findViewById(R.id.google)
//        googleSignInButton.setOnClickListener {
//            signIn()
//        }
//
//        // Handle "Don't have an account? Log in" click
//        val txtLogin: TextView = findViewById(R.id.txtlogin)
//        txtLogin.setOnClickListener {
//            Log.d(TAG, "Don't have an account? Log in")
//            startActivity(Intent(this, Login::class.java)) // Assuming LoginActivity is the correct name
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in and update UI
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
//                    signInLauncher.launch(
//                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
//                    )
//                } catch (e: Exception) {
//                    Log.e(TAG, "Error starting sign-in intent: ${e.message}")
//                    Toast.makeText(this, "Error starting sign-in: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener(this) { e ->
//                Log.e(TAG, "Sign-in failed: ${e.message}")
//                Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
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
//                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                    auth.signInWithCredential(firebaseCredential)
//                        .addOnCompleteListener(this) { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "signInWithCredential:success")
//                                val user = auth.currentUser
//                                updateUI(user)
//
//                                // Start the Categories Activity on successful sign-in
//                                startActivity(Intent(this, Categories::class.java))
//                                finish()
//                            } else {
//                                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                                updateUI(null)
//                            }
//                        }
//                } else {
//                    Log.e(TAG, "No ID token found!")
//                    Toast.makeText(this, "No ID token found, please try again.", Toast.LENGTH_SHORT).show()
//                }
//            } ?: Log.e(TAG, "Received null data for sign-in.")
//        } catch (e: ApiException) {
//            Log.e(TAG, "Sign-in failed: ${e.message}")
//            Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
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








//
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.textfield.TextInputLayout
//
//class Signup : AppCompatActivity() {
//
//    private lateinit var tilFirstName: TextInputLayout
//    private lateinit var tilLastName: TextInputLayout
//    private lateinit var tilEmail: TextInputLayout
//    private lateinit var tilPassword: TextInputLayout
//    private lateinit var tilConfirmPassword: TextInputLayout
//
//    private lateinit var etFirstName: EditText
//    private lateinit var etLastName: EditText
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var etConfirmPassword: EditText
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signup) // Ensure this matches your layout's name
//
//        // Initialize Views
//        tilFirstName = findViewById(R.id.tilfirstname)
//        tilLastName = findViewById(R.id.tilLastName)
//        tilEmail = findViewById(R.id.tilEmail)
//        tilPassword = findViewById(R.id.tilPassword)
//        tilConfirmPassword = findViewById(R.id.tilpass)
//
//        etFirstName = findViewById(R.id.etfirstname)
//        etLastName = findViewById(R.id.etLastname)
//        etEmail = findViewById(R.id.etmail)
//        etPassword = findViewById(R.id.etpss)
//        etConfirmPassword = findViewById(R.id.etpass)
//
//        val btnSignUp: Button = findViewById(R.id.btnlogin)
//
//        btnSignUp.setOnClickListener {
//            if (validateFields()) {
//                // Proceed with signup
//                Toast.makeText(this, "All fields are valid", Toast.LENGTH_SHORT).show()
//                // Handle the signup logic here
//            }
//        }
//    }
//
//    private fun validateFields(): Boolean {
//        var isValid = true
//
//        // Validate First Name
//        if (etFirstName.text.toString().trim().isEmpty()) {
//            tilFirstName.error = "First name is required"
//            isValid = false
//        } else {
//            tilFirstName.error = null
//        }
//
//        // Validate Last Name
//        if (etLastName.text.toString().trim().isEmpty()) {
//            tilLastName.error = "Last name is required"
//            isValid = false
//        } else {
//            tilLastName.error = null
//        }
//
//        // Validate Email
//        if (etEmail.text.toString().trim().isEmpty()) {
//            tilEmail.error = "Email is required"
//            isValid = false
//        } else {
//            tilEmail.error = null
//        }
//
//        // Validate Password
//        if (etPassword.text.toString().trim().isEmpty()) {
//            tilPassword.error = "Password is required"
//            isValid = false
//        } else {
//            tilPassword.error = null
//        }
//
//        // Validate Confirm Password
//        if (etConfirmPassword.text.toString().trim().isEmpty()) {
//            tilConfirmPassword.error = "Confirm password is required"
//            isValid = false
//        } else if (etConfirmPassword.text.toString() != etPassword.text.toString()) {
//            tilConfirmPassword.error = "Passwords do not match"
//            isValid = false
//        } else {
//            tilConfirmPassword.error = null
//        }
//
//        return isValid
//    }
//}











import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser

//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var oneTapClient: com.google.android.gms.auth.api.identity.SignInClient
//    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>
//
//    // Add new EditText variables for regular sign up
//    private lateinit var etFirstName: EditText
//    private lateinit var etLastName: EditText
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var etConfirmPassword: EditText
//
//    private val TAG = "SignUpActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signup) // Ensure your layout name is correct
//
//        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
//        oneTapClient = Identity.getSignInClient(this)
//
//        // Initialize your views
//        etFirstName = findViewById(R.id.etfirstname)
//        etLastName = findViewById(R.id.etLastname)
//        etEmail = findViewById(R.id.etmail)
//        etPassword = findViewById(R.id.etpss)
//        etConfirmPassword = findViewById(R.id.etpass)
//
//        signInLauncher = registerForActivityResult(
//            ActivityResultContracts.StartIntentSenderForResult()
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                handleSignInResult(result.data)
//            } else {
//                Log.e(TAG, "Sign-in failed or cancelled.")
//                Toast.makeText(this, "Sign-in failed or cancelled.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        // Handle Google sign-in button click
//        val googleSignInButton: Button = findViewById(R.id.google)
//        googleSignInButton.setOnClickListener {
//            signIn()
//        }
//
//        // Handle traditional sign-up button click
//        val btnSignUp: Button = findViewById(R.id.btnlogin)
//        btnSignUp.setOnClickListener {
//            performSignUp()
//        }
//
//        // Handle "Already have an account? Log in" click
//        val txtLogin: TextView = findViewById(R.id.txtlogin)
//        txtLogin.setOnClickListener {
//            Log.d(TAG, "Navigate to Login")
//            startActivity(Intent(this, Login::class.java)) // Update with your actual Login Activity name
//        }
//    }
//
//    private fun performSignUp() {
//        val firstName = etFirstName.text.toString().trim()
//        val lastName = etLastName.text.toString().trim()
//        val email = etEmail.text.toString().trim()
//        val password = etPassword.text.toString().trim()
//        val confirmPassword = etConfirmPassword.text.toString().trim()
//
//        // Basic validation
//        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (password != confirmPassword) {
//            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // You may want to add more validation here (e.g., email format)
//
//        // Create user with email and password
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "User registered successfully")
//                    // User successfully registered, navigate to home
//                    startActivity(Intent(this, HomeFragment::class.java)) // Update with your actual Home Activity name
//                    finish()
//                } else {
//                    Log.e(TAG, "Registration failed", task.exception)
//                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun signIn() {
//        val signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId(getString(R.string.server_client_id)) // Your server client ID
//                    .setFilterByAuthorizedAccounts(false)
//                    .build()
//            )
//            .build()
//
//        oneTapClient.beginSignIn(signInRequest)
//            .addOnSuccessListener(this) { result ->
//                try {
//                    signInLauncher.launch(
//                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
//                    )
//                } catch (e: Exception) {
//                    Log.e(TAG, "Error starting sign-in intent: ${e.message}")
//                    Toast.makeText(this, "Error starting sign-in: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener(this) { e ->
//                Log.e(TAG, "Sign-in failed: ${e.message}")
//                Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
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
//                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                    auth.signInWithCredential(firebaseCredential)
//                        .addOnCompleteListener(this) { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "signInWithCredential:success")
//                                val user = auth.currentUser
//                                updateUI(user)
//
//                                // Start the Home Activity on successful sign-in
//                                startActivity(Intent(this, HomeFragment::class.java)) // Update with your actual Home Activity name
//                                finish()
//                            } else {
//                                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                                updateUI(null)
//                            }
//                        }
//                } else {
//                    Log.e(TAG, "No ID token found!")
//                    Toast.makeText(this, "No ID token found, please try again.", Toast.LENGTH_SHORT).show()
//                }
//            } ?: Log.e(TAG, "Received null data for sign-in.")
//        } catch (e: ApiException) {
//            Log.e(TAG, "Sign-in failed: ${e.message}")
//            Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
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
//}


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: com.google.android.gms.auth.api.identity.SignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>

    // Add new EditText variables for regular sign up
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup) // Ensure your layout name is correct

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(this)

        // Initialize your views
        etFirstName = findViewById(R.id.etfirstname)
        etLastName = findViewById(R.id.etLastname)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etpss)
        etConfirmPassword = findViewById(R.id.etpass)

        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSignInResult(result.data)
            } else {
                Log.e(TAG, "Sign-in failed or cancelled.")
                Toast.makeText(this, "Sign-in failed or cancelled.", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Google sign-in button click
        val googleSignInButton: Button = findViewById(R.id.google)
        googleSignInButton.setOnClickListener {
            signIn()
        }

        // Handle traditional sign-up button click
        val btnSignUp: Button = findViewById(R.id.btnlogin)
        btnSignUp.setOnClickListener {
            performSignUp()
        }

        // Handle "Already have an account? Log in" click
        val txtLogin: TextView = findViewById(R.id.txtlogin)
        txtLogin.setOnClickListener {
            Log.d(TAG, "Navigate to Login")
            startActivity(Intent(this, Login::class.java)) // Update with your actual Login Activity name
        }
    }

    private fun performSignUp() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Basic validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Create user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User registered successfully")
                    // User successfully registered, navigate to CategoriesActivity
                    val intent = Intent(this,Login::class.java)
                    startActivity(intent)
                    finish() // Close the SignUpActivity so the user can't return here with back button
                } else {
                    Log.e(TAG, "Registration failed", task.exception)
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)  // Ensure this is set to true
                    .setServerClientId(getString(R.string.server_client_id)) // Your server client ID
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    signInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error starting sign-in intent: ${e.message}")
                    Toast.makeText(this, "Error starting sign-in: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(this) { e ->
                Log.e(TAG, "Sign-in failed: ${e.message}")
                Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleSignInResult(data: Intent?) {
        try {
            data?.let {
                val credential = oneTapClient.getSignInCredentialFromIntent(it)
                val idToken = credential.googleIdToken

                if (!idToken.isNullOrEmpty()) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "signInWithCredential:success")
                                val user = auth.currentUser
                                updateUI(user)

                                // Navigate to CategoriesActivity on successful sign-in
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish() // Close SignUpActivity
                            } else {
                                Log.w(TAG, "signInWithCredential:failure", task.exception)
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                updateUI(null)
                            }
                        }
                } else {
                    Log.e(TAG, "No ID token found!")
                    Toast.makeText(this, "No ID token found, please try again.", Toast.LENGTH_SHORT).show()
                }
            } ?: Log.e(TAG, "Received null data for sign-in.")
        } catch (e: ApiException) {
            Log.e(TAG, "Sign-in failed: ${e.message}")
            Toast.makeText(this, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d(TAG, "User signed in: ${user.displayName}")
        } else {
            Log.d(TAG, "No user signed in")
        }
    }
}