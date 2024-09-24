package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 100
    private val TAG = "Signup"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        configureGoogleSignIn()


        setupClickListeners()
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupClickListeners() {
        binding.google.setOnClickListener {
            signInWithGoogle()
        }

        binding.txtlogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(Exception::class.java)
            account?.let {
                authenticateWithFirebase(it.idToken)
            } ?: run {
                showErrorSnackbar("Google sign-in failed: No account")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Google sign-in failed", e)
            showErrorSnackbar("Google sign-in failed: ${e.message}")
        }
    }

    private fun authenticateWithFirebase(idToken: String?) {
        idToken?.let {
            val firebaseCredential = GoogleAuthProvider.getCredential(it, null)
            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        updateUI(auth.currentUser)
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        showErrorSnackbar("Authentication Failed.")
                        updateUI(null)
                    }
                }
        } ?: run {
            showErrorSnackbar("No ID token!")
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            Snackbar.make(binding.root, "Signed in as ${it.displayName}", Snackbar.LENGTH_SHORT).show()
        } ?: run {
            Snackbar.make(binding.root, "Not signed in", Snackbar.LENGTH_SHORT).show()
        }
    }
    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}

















