package com.akirachix.dishhub

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ShopFragment : Fragment() {
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private val shoppingList = mutableListOf<ShoppingItem>()
    private var itemIdCounter = 0
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = SessionManager(requireContext()).getUserId()

        val addButton: Button = view.findViewById(R.id.addButton)
        val saveButton: Button = view.findViewById(R.id.btnsave)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewShoppingList)
        val textInputNameLayout: TextInputLayout = view.findViewById(R.id.textInputLayoutName)
        val textInputQuantityLayout: TextInputLayout = view.findViewById(R.id.textInputLayoutQuantity)
        val textInputName: TextInputEditText = textInputNameLayout.editText as TextInputEditText
        val textInputQuantity: TextInputEditText = textInputQuantityLayout.editText as TextInputEditText
        val profileButton: ImageView = view.findViewById(R.id.btntoprofile)

        // Initialize adapter
        shoppingListAdapter = ShoppingListAdapter(shoppingList) { itemToDelete ->
            shoppingListAdapter.removeItem(itemToDelete)
            saveShoppingList(shoppingList)  // Ensure to save after removal
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = shoppingListAdapter

        loadShoppingList()

        addButton.setOnClickListener {
            val itemName = textInputName.text.toString().trim()
            val itemQuantity = textInputQuantity.text.toString().trim()

            if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val newItem = ShoppingItem(id = itemIdCounter++, name = itemName, quantityWithUnit = itemQuantity)
                shoppingListAdapter.addItem(newItem)
                saveShoppingList(shoppingList)  // Save shopping list on addition

                textInputName.text?.clear()
                textInputQuantity.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter both item name and quantity with unit.", Toast.LENGTH_SHORT).show()
            }
        }

        saveButton.setOnClickListener {
            saveShoppingList(shoppingList)
        }

        profileButton.setOnClickListener {
            val intent = Intent(requireContext(), Profile::class.java)
            startActivity(intent)
        }
    }

    private fun saveShoppingList(list: List<ShoppingItem>) {
        userId?.let { uid ->
            val sharedPreferences = requireContext().getSharedPreferences("shopping_list_$uid", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()

            // Save the list
            val jsonString = gson.toJson(list)
            editor.putString("list", jsonString)
            editor.putInt("item_id_counter", itemIdCounter)

            editor.apply()
            Log.d("ShopFragment", "Shopping list saved: $jsonString")
            Toast.makeText(requireContext(), "Shopping list saved!", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "Error: User not logged in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadShoppingList() {
        userId?.let { uid ->
            val sharedPreferences = requireContext().getSharedPreferences("shopping_list_$uid", Context.MODE_PRIVATE)
            val gson = Gson()
            val jsonString = sharedPreferences.getString("list", null)

            // Load the itemIdCounter
            itemIdCounter = sharedPreferences.getInt("item_id_counter", 0)

            if (jsonString != null) {
                val type = object : TypeToken<MutableList<ShoppingItem>>() {}.type
                val savedList: MutableList<ShoppingItem> = gson.fromJson(jsonString, type)

                shoppingList.clear()
                shoppingList.addAll(savedList)
                shoppingListAdapter.notifyDataSetChanged()

                Log.d("ShopFragment", "Shopping list loaded: $savedList")
            } else {
                Log.d("ShopFragment", "No saved shopping list found.")
            }
        } ?: run {
            Toast.makeText(requireContext(), "Error: User not logged in.", Toast.LENGTH_SHORT).show()
        }
    }
}







