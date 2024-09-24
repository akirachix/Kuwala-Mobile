package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class Profile : Fragment() {

    private lateinit var adapter: ShoppingListAdapter
    private val shoppingItems = mutableListOf<ShoppingItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewShoppingList)
        val addButton: Button = view.findViewById(R.id.addButton)
        val inputLayout: TextInputLayout = view.findViewById(R.id.textInputLayout2)
        val profileButton: ImageView = view.findViewById(R.id.btntoprofile)

        adapter = ShoppingListAdapter(
            shoppingItems,
            onItemChecked = { updateItem(it) },
            onItemDeleted = { deleteItem(it) }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        addButton.setOnClickListener {
            val itemName = inputLayout.editText?.text.toString()
            if (itemName.isNotEmpty()) {
                addItem(itemName)
                inputLayout.editText?.text?.clear()
            }
        }


        profileButton.setOnClickListener {
            val intent = Intent(requireContext(), Profile::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun addItem(name: String) {
        val newItem = ShoppingItem(shoppingItems.size, name, 1)
        shoppingItems.add(newItem)
        adapter.updateItems(shoppingItems)
    }

    private fun updateItem(item: ShoppingItem) {
        val index = shoppingItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            shoppingItems[index] = item
            adapter.updateItems(shoppingItems)
        }
    }

    private fun deleteItem(item: ShoppingItem) {
        shoppingItems.removeAll { it.id == item.id }
        adapter.updateItems(shoppingItems)
    }
}
