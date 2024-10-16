
package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.akirachix.DineHub.databinding.ActivityAddItemManuallyBinding
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
        val foodName = binding.etFoodName.editText?.text.toString().trim()

        if (foodName.isNotEmpty()) {

            val sharedPreferences = getSharedPreferences("PantryPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val foodItem = "$foodName,pantry,$quantity"

            val pantryItems = sharedPreferences.getString("PantryItems", "") ?: ""
            editor.putString("PantryItems", if (pantryItems.isNotEmpty()) "$pantryItems|$foodItem" else foodItem)

            editor.apply()

            toast("Food item saved: $foodName, Quantity: $quantity")

            val resultIntent = Intent().apply {
                putExtra("newItem", foodItem)
            }
            setResult(RESULT_OK, resultIntent)
            finish() // Close the activity
        } else {
            toast("Please enter food name.")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}