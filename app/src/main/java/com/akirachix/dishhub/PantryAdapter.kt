
package com.akirachix.dishhub

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PantryAdapter(
    private val context: Context,
    private var pantryItems: MutableList<PantryItems>,
    private val selectedIngredients: MutableList<String>,
    private val onPantryItemChange: () -> Unit
) : RecyclerView.Adapter<PantryAdapter.ViewHolder>() {

    private var filteredItems = pantryItems.toList()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("PantryPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    init {
        loadPantryItems()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val itemName: TextView = itemView.findViewById(R.id.item)
        private val quantityText: TextView = itemView.findViewById(R.id.quantity)
        private val minusButton: ImageView = itemView.findViewById(R.id.negative)

        fun bind(ingredient: PantryItems) {
            itemName.text = ingredient.item
            itemName.visibility = View.VISIBLE
            quantityText.text = ingredient.quantity.toString()
            checkbox.isChecked = selectedIngredients.contains(ingredient.item)

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedIngredients.add(ingredient.item)
                } else {
                    selectedIngredients.remove(ingredient.item)
                }
                onPantryItemChange()
                saveSelectedVegetables()
            }

            minusButton.setOnClickListener {
                val currentQuantity = ingredient.quantity.toInt()
                if (currentQuantity > 1) {
                    ingredient.quantity = (currentQuantity - 1).toString() // Decrement the quantity
                    quantityText.text = ingredient.quantity // Update the quantity displayed
                    savePantryItems() // Save updated items
                    onPantryItemChange() // Notify about the change
                }
            }

            itemView.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }

    override fun getItemCount() = filteredItems.size

    fun updateItems(newItems: List<PantryItems>) {
        pantryItems.clear()
        pantryItems.addAll(newItems)
        filteredItems = pantryItems.toList()
        println("Debug: Pantry items updated - ${pantryItems.map { it.item }}")
        savePantryItems()
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            pantryItems.toList()
        } else {
            pantryItems.filter { it.item.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    private fun savePantryItems() {
        val jsonString = gson.toJson(pantryItems)
        sharedPreferences.edit().putString("pantry_items", jsonString).apply()
    }

    private fun loadPantryItems() {
        val jsonString = sharedPreferences.getString("pantry_items", null)
        if (jsonString != null) {
            val type = object : TypeToken<List<PantryItems>>() {}.type
            val loadedItems: List<PantryItems> = gson.fromJson(jsonString, type)
            pantryItems.clear()
            pantryItems.addAll(loadedItems)
            filteredItems = pantryItems.toList()
            notifyDataSetChanged()
        }
    }

    private fun saveSelectedVegetables() {
        val jsonSelectedIngredients = gson.toJson(selectedIngredients)
        sharedPreferences.edit().putString("selected_ingredients", jsonSelectedIngredients).apply()
    }
}


















