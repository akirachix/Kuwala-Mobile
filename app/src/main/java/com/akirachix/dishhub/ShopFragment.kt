package com.akirachix.dishhub

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val addButton: Button = view.findViewById(R.id.addButton)
        val saveButton: Button = view.findViewById(R.id.btnsave)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewShoppingList)
        val textInputNameLayout: TextInputLayout = view.findViewById(R.id.textInputLayoutName)
        val textInputQuantityLayout: TextInputLayout = view.findViewById(R.id.textInputLayoutQuantity)
        val textInputName: TextInputEditText = textInputNameLayout.editText as TextInputEditText
        val textInputQuantity: TextInputEditText = textInputQuantityLayout.editText as TextInputEditText


        val profileButton: ImageView = view.findViewById(R.id.btntoprofile)
        profileButton.setOnClickListener {
            val intent = Intent(requireContext(), Profile::class.java)
            startActivity(intent)
        }

        shoppingListAdapter = ShoppingListAdapter(shoppingList) { itemToDelete ->
            shoppingListAdapter.removeItem(itemToDelete)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = shoppingListAdapter


        addButton.setOnClickListener {
            val itemName = textInputName.text.toString().trim()
            val itemQuantity = textInputQuantity.text.toString().trim()

            if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val newItem = ShoppingItem(id = itemIdCounter++, name = itemName, quantityWithUnit = itemQuantity)
                shoppingListAdapter.addItem(newItem)

                textInputName.text?.clear()
                textInputQuantity.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter both item name and quantity with unit.", Toast.LENGTH_SHORT).show()
            }
        }

        saveButton.setOnClickListener {
            saveShoppingList(shoppingList)
        }


        loadShoppingList()
    }

    private fun saveShoppingList(list: List<ShoppingItem>) {
        val sharedPreferences = requireContext().getSharedPreferences("shopping_list", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonString = gson.toJson(list)
        editor.putString("list", jsonString)
        editor.apply()
        Toast.makeText(requireContext(), "Shopping list saved!", Toast.LENGTH_SHORT).show()
    }

    private fun loadShoppingList() {
        val sharedPreferences = requireContext().getSharedPreferences("shopping_list", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonString = sharedPreferences.getString("list", null)
        val type = object : TypeToken<MutableList<ShoppingItem>>() {}.type

        jsonString?.let {
            val savedList: MutableList<ShoppingItem> = gson.fromJson(it, type)
            shoppingList.clear()
            shoppingList.addAll(savedList)
            shoppingListAdapter.notifyDataSetChanged()
        }
    }
}