
package com.akirachix.dishhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputLayout

class Shopping : AppCompatActivity() {
    private lateinit var adapter: ShoppingListAdapter
    private val shoppingItems = mutableListOf<ShoppingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewShoppingList)
        val addButton: Button = findViewById(R.id.addButton)

        val inputLayout: TextInputLayout = findViewById(R.id.textInputLayout2)
        val profileButton: ImageView = findViewById(R.id.btntoprofile)

        adapter = ShoppingListAdapter(
            shoppingItems,
            onItemChecked = { updateItem(it) },
            onItemDeleted = { deleteItem(it) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            val itemName = inputLayout.editText?.text.toString()
            if (itemName.isNotEmpty()) {
                addItem(itemName)
                inputLayout.editText?.text?.clear()
            }
        }


        profileButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
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