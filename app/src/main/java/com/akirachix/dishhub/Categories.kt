

package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.akirachix.dishhub.databinding.ActivityCategoriesBinding

class Categories : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Check for the intent extra to load PantryFragment
        if (intent.getStringExtra("showFragment") == "pantry") {
            loadFragment(PantryFragment()) // Load the PantryFragment directly
            binding.bottomNavigationView.selectedItemId = R.id.pantry // Set the selected nav item
        } else {
            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        loadFragment(HomeFragment())
                        true
                    }
                    R.id.pantry -> {
                        loadFragment(PantryFragment())
                        true
                    }
                    R.id.shop -> {
                        loadFragment(ShopFragment())
                        true
                    }
                    else -> false
                }
            }

            // Load the default fragment
            if (savedInstanceState == null) {
                loadFragment(HomeFragment())
                binding.bottomNavigationView.selectedItemId = R.id.home
            }
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcvHome, fragment).commit()
    }

    private fun showLogoutConfirmationDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Logout")
        dialog.setMessage("Are you sure you want to log out?")
        dialog.setPositiveButton("Yes") { _, _ ->
            logout()
        }
        dialog.setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss() }
        dialog.show()
    }

    private fun logout() {
        sessionManager.logout()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}