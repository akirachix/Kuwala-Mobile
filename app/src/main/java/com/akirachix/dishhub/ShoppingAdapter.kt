
package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.R
data class Shop(
    var name: String,
    var quantity: Int,
    var isChecked: Boolean = false
)
class ShoppingListAdapter(
    private var items: List<ShoppingItem>,
    private val onItemChecked: (ShoppingItem) -> Unit,
    private val onItemDeleted: (ShoppingItem) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBoxItem)
        val textViewName: TextView = view.findViewById(R.id.textViewItemName)
        val textViewQuantity: TextView = view.findViewById(R.id.textViewItemQuantity)
        val imageViewDelete: ImageView = view.findViewById(R.id.imageViewDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_list_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.checkBox.isChecked = item.isChecked
        holder.textViewName.text = item.name
        holder.textViewQuantity.text = item.quantity.toString()

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            onItemChecked(item)
        }

        holder.imageViewDelete.setOnClickListener {
            onItemDeleted(item)
        }
    }
    override fun getItemCount() = items.size

    fun updateItems(newItems: List<ShoppingItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}