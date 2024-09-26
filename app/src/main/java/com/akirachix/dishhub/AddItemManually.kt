package com.akirachix.dishhub

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.databinding.ActivityAddItemManuallyBinding

class AddItemManually : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemManuallyBinding
    private var quantity: Int = 4
    private lateinit var spinnerCategory: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemManuallyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerCategory = binding.spinnerCategory
        ArrayAdapter.createFromResource(
            this,
            R.array.food_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }

        binding.addCircle.setOnClickListener { increaseQuantity() }
        binding.subtractCircle.setOnClickListener { decreaseQuantity() }
        binding.button5.setOnClickListener { finish() }
        binding.button6.setOnClickListener { saveItem() }
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
        val foodName = binding.edFoodName.text.toString()
        val category = spinnerCategory.selectedItem.toString()

        if (foodName.isNotEmpty() && category.isNotEmpty()) {
            toast("Food item saved: $foodName, Category: $category")
            finish()
        } else {
            toast("Please enter food name and select a category.")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}