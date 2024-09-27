
package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class ShoppingListAdapter(
    private val shoppingList: MutableList<ShoppingItem>,
    private val onDelete: (ShoppingItem) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, parent, false)
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val currentItem = shoppingList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = shoppingList.size

    fun addItem(item: ShoppingItem) {
        shoppingList.add(item)
        notifyItemInserted(shoppingList.size - 1)
    }

    fun removeItem(item: ShoppingItem) {
        val position = shoppingList.indexOf(item)
        if (position != -1) {
            shoppingList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.textViewItemName)
        private val itemQuantityTextView: TextView = itemView.findViewById(R.id.textViewItemQuantity)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.imageViewDelete)

        fun bind(item: ShoppingItem) {
            itemNameTextView.text = item.name
            itemQuantityTextView.text = "Qty: ${item.quantityWithUnit}"

            deleteImageView.setOnClickListener {
                onDelete(item)
            }
        }
    }
}