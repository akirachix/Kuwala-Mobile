
package com.akirachix.dishhub

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityAddItemManuallyBinding

class AddItemManually : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemManuallyBinding
    private var quantity: Int = 4

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
        val foodName = binding.etFoodName.editText?.text.toString().trim() // Get food name

        if (foodName.isNotEmpty()) {
            // Save the item in SharedPreferences
            val sharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Create a food item string in the format of foodName,pantry,quantity
            val foodItem = "$foodName,pantry,$quantity" // All items will now save as "pantry" category

            // Append the food item to the pantry items
            val pantryItems = sharedPreferences.getString("PantryItems", "") ?: ""
            editor.putString("PantryItems", if (pantryItems.isNotEmpty()) "$pantryItems|$foodItem" else foodItem)

            editor.apply() // Apply changes to SharedPreferences

            toast("Food item saved: $foodName, Quantity: $quantity")
            finish() // Close the activity
        } else {
            toast("Please enter food name.")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



