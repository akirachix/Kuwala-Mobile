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

        // Get userId from SessionManager
        userId = SessionManager(requireContext()).getUserId()

        setupViews(view)
        loadShoppingList() // Load saved items when fragment is created
    }

    private fun setupViews(view: View) {
        val addButton: Button = view.findViewById(R.id.addButton)
        val saveButton: Button = view.findViewById(R.id.btnsave)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewShoppingList)
        val textInputNameLayout: TextInputLayout = view.findViewById(R.id.textInputLayoutName)
        val textInputQuantityLayout: TextInputLayout = view.findViewById(R.id.textInputLayoutQuantity)
        val textInputName: TextInputEditText = textInputNameLayout.editText as TextInputEditText
        val textInputQuantity: TextInputEditText = textInputQuantityLayout.editText as TextInputEditText
        val profileButton: ImageView = view.findViewById(R.id.btntoprofile)

        // Initialize adapter with delete callback
        shoppingListAdapter = ShoppingListAdapter(shoppingList) { itemToDelete ->
            shoppingList.remove(itemToDelete)
            shoppingListAdapter.notifyDataSetChanged()
            saveShoppingList(shoppingList) // Save after deletion
        }

        // Setup RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = shoppingListAdapter
        }

        // Add button click listener
        addButton.setOnClickListener {
            val itemName = textInputName.text.toString().trim()
            val itemQuantity = textInputQuantity.text.toString().trim()

            if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val newItem = ShoppingItem(
                    id = itemIdCounter++,
                    name = itemName,
                    quantityWithUnit = itemQuantity
                )
                shoppingList.add(newItem)
                shoppingListAdapter.notifyItemInserted(shoppingList.size - 1)
                saveShoppingList(shoppingList) // Save after adding

                // Clear input fields
                textInputName.text?.clear()
                textInputQuantity.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter both item name and quantity", Toast.LENGTH_SHORT).show()
            }
        }

        // Save button click listener
        saveButton.setOnClickListener {
            saveShoppingList(shoppingList)
        }

        profileButton.setOnClickListener {
            startActivity(Intent(requireContext(), Profile::class.java))
        }
    }

    private fun saveShoppingList(list: List<ShoppingItem>) {
        userId?.let { uid ->
            try {
                val sharedPreferences = requireContext().getSharedPreferences(
                    "shopping_list_$uid",
                    Context.MODE_PRIVATE
                )
                val editor = sharedPreferences.edit()
                val gson = Gson()

                val jsonString = gson.toJson(list)
                editor.putString("list", jsonString)
                editor.putInt("item_id_counter", itemIdCounter)
                editor.apply()

                Toast.makeText(requireContext(), "Shopping list saved successfully!", Toast.LENGTH_SHORT).show()
                Log.d("ShopFragment", "Shopping list saved: $jsonString")
            } catch (e: Exception) {
                Log.e("ShopFragment", "Error saving shopping list", e)
                Toast.makeText(requireContext(), "Error saving shopping list", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(requireContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadShoppingList() {
        userId?.let { uid ->
            try {
                val sharedPreferences = requireContext().getSharedPreferences(
                    "shopping_list_$uid",
                    Context.MODE_PRIVATE
                )
                val gson = Gson()
                val jsonString = sharedPreferences.getString("list", null)
                itemIdCounter = sharedPreferences.getInt("item_id_counter", 0)

                if (jsonString != null) {
                    val type = object : TypeToken<List<ShoppingItem>>() {}.type
                    val savedList: List<ShoppingItem> = gson.fromJson(jsonString, type)

                    shoppingList.clear()
                    shoppingList.addAll(savedList)
                    shoppingListAdapter.notifyDataSetChanged()

                    Log.d("ShopFragment", "Shopping list loaded: $savedList")
                }
            } catch (e: Exception) {
                Log.e("ShopFragment", "Error loading shopping list", e)
                Toast.makeText(requireContext(), "Error loading shopping list", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(requireContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}





