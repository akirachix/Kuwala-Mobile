

package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GrainsAdapter(
    private var items: List<Grains>,
    private val itemClick: (Grains) -> Unit
) : RecyclerView.Adapter<GrainsAdapter.GrainsViewHolder>() {

    // Read-only property to expose the current list of items
    val currentList: List<Grains>
        get() = items

    class GrainsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
        val plusButton: ImageView = view.findViewById(R.id.btnpantrytoprofile)
        val minusButton: ImageView = view.findViewById(R.id.imageView16)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrainsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_grains, parent, false)
        return GrainsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrainsViewHolder, position: Int) {
        val item = items[position]
        holder.foodName.text = item.name
        holder.quantity.text = item.quantity.toString()
        holder.checkBox.isChecked = item.isSelected

        // Set the checkbox change listener
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            itemClick(item) // Notify the activity or fragment
        }

        // Increment quantity
        holder.plusButton.setOnClickListener {
            item.quantity++
            holder.quantity.text = item.quantity.toString()
        }

        // Decrement quantity
        holder.minusButton.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                holder.quantity.text = item.quantity.toString()
            }
        }

        // Handle item click
        holder.itemView.setOnClickListener { itemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Grains>) {
        items = newItems
        notifyDataSetChanged()
    }
}