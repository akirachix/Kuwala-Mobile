package com.akirachix.dishhub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.dishhub.R.*
import com.akirachix.dishhub.databinding.ActivityAddItemManuallyBinding

class AddItemManually : AppCompatActivity() {
    lateinit var binding: ActivityAddItemManuallyBinding


    private var quantity: Int = 4 // Default starting quantity
    private lateinit var displayQuantity: TextView
    private lateinit var foodNameEditText: EditText
    private lateinit var categoryEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemManuallyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addCircle: ImageView = findViewById(id.addCircle)
        val subtractCircle: ImageView = findViewById(id.subtractCircle)
        val cancelButton: Button = findViewById(id.button5)
        val saveButton: Button = findViewById(id.button6)
        val backArrow: ImageView = findViewById(id.backArrow)

        addCircle.setOnClickListener {
            increaseQuantity()
        }

        subtractCircle.setOnClickListener {
            decreaseQuantity()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            saveItem()
        }

        backArrow.setOnClickListener {
            finish()
        }
    }

    private fun increaseQuantity() {
        quantity++
        displayQuantity.text = quantity.toString()
    }

    private fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
            displayQuantity.text = quantity.toString()
        }
    }

    private fun saveItem() {
        val foodName = foodNameEditText.text.toString()
        val category = categoryEditText.text.toString()

        if (foodName.isNotEmpty() && category.isNotEmpty()) {

            finish()
        } else {

            toast("Please enter food name and category.")
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}