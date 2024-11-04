
package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityAddItemManuallyBinding
import com.google.android.ads.mediationtestsuite.activities.HomeActivity

class AddItemManually : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemManuallyBinding
    private var quantity: Int = 1 // Start from 1 to avoid confusion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemManuallyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addCircle.setOnClickListener { increaseQuantity() }
        binding.subtractCircle.setOnClickListener { decreaseQuantity() }
        binding.button5.setOnClickListener { finish() } // Cancel button
        binding.button6.setOnClickListener { saveItem() } // Save button
        binding.backArrow.setOnClickListener { finish() }

        displayQuantity()
    }

    private fun increaseQuantity() {
        quantity++
        displayQuantity()
    }

    private fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
            displayQuantity()
        }
    }

    private fun displayQuantity() {
        binding.abQuantity.text = quantity.toString()
    }

    private fun saveItem() {
        val foodName = binding.etFoodName.editText?.text.toString().trim()

        if (foodName.isNotEmpty()) {
            val sharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val foodItem = "$foodName,pantry,$quantity"

            val pantryItems = sharedPreferences.getString("PantryItems", "") ?: ""
            editor.putString(
                "PantryItems",
                if (pantryItems.isNotEmpty()) "$pantryItems|$foodItem" else foodItem
            )

            editor.apply()

            toast("Food item saved: $foodName, Quantity: $quantity")

            // Present user with options to navigate elsewhere or close
            navigateAfterSave()
        } else {
            toast("Please enter food name.")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateAfterSave() {
        // You may want to use a dialog or a simple Toast
        // to allow the user to choose whether to continue.
        val intent = Intent(this, Categories::class.java)
        intent.putExtra("showFragment", "pantry") // optionally open pantry
        startActivity(intent)
        finish()


    }
}














//    private fun navigateAfterSave() {
//        val options = arrayOf("Go to Pantry")
//
//        // Create an alert dialog to present navigation options
//        AlertDialog.Builder(this)
//            .setTitle("Navigate")
//            .setItems(options) { _, which ->
//                when (which) {
//                    0 -> navigateToFragment("pantry")
//
//                }
//            }
//            .show()
//    }
//
//    private fun navigateToFragment(fragment: String) {
//        val intent = Intent(this, Categories::class.java)
//        intent.putExtra("showFragment", fragment) // Pass the selected fragment
//        startActivity(intent)
//        finish() // Close the current activity
//    }
//}