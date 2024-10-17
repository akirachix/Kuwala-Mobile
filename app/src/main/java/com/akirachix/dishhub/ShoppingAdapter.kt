




package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.R


class ShoppingListAdapter(
    private val items: MutableList<ShoppingItem>,
    private val onDeleteClick: (ShoppingItem) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.textViewItemName)
        val itemQuantity: TextView = view.findViewById(R.id.textViewItemQuantity)
        // Changed from ImageButton to ImageView
        val deleteButton: ImageView = view.findViewById(R.id.imageViewDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemQuantity.text = item.quantityWithUnit
        holder.deleteButton.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount() = items.size
}






