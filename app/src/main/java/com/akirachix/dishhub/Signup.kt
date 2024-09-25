
package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import android.widget.Button

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: com.google.android.gms.auth.api.identity.SignInClient
    private val TAG = "SignUpActivity"
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Help to  Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(this)

        // Help to Register the sign-in launcher for handling the result of the sign-in intent
        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSignInResult(result.data)
            } else {
                Log.e(TAG, "Sign-in failed or cancelled.")
            }
        }

        // Help to  Set up the Google Sign-In button click listener
        val googleSignInButton: Button = findViewById(R.id.google)
        googleSignInButton.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        // To  Check if user is signed in .
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.server_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    signInLauncher.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
                } catch (e: Exception) {
                    Log.e(TAG, "Error starting sign-in intent: ${e.message}")
                }
            }
            .addOnFailureListener(this) { e ->
                Log.e(TAG, "Sign-in failed: ${e.message}")
            }
    }

    private fun handleSignInResult(data: Intent?) {
        try {
            data?.let {
                val credential = oneTapClient.getSignInCredentialFromIntent(it)
                val idToken = credential.googleIdToken

                if (!idToken.isNullOrEmpty()) {
                    // When I get   an ID token from Google. I use it to authenticate with Firebase.
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "signInWithCredential:success")
                                val user = auth.currentUser
                                updateUI(user)

                                //Help to  Navigate to the categories activity after successful sign-in
                                startActivity(Intent(this, Categories::class.java))
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

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d(TAG, "User signed in: ${user.displayName}")
        } else {
            Log.d(TAG, "No user signed in")
        }
    }

    private fun signOut() {
        auth.signOut()
        Log.d(TAG, "User signed out")
    }
}