package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.R

class FruitsAdapter(
    private var items: List<Fruits>,
    private val itemClick: (Fruits) -> Unit
) : RecyclerView.Adapter<FruitsAdapter.FruitsViewHolder>() {

    class FruitsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
        val plusButton: ImageView = view.findViewById(R.id.btnpantrytoprofile)
        val minusButton: ImageView = view.findViewById(R.id.imageView16)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_fruits, parent, false)
        return FruitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FruitsViewHolder, position: Int) {
        val item = items[position]
        holder.foodName.text = item.name
        holder.quantity.text = item.quantity.toString()

        holder.checkBox.isChecked = item.isSelected

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked // Update the isSelected property
        }

        holder.plusButton.setOnClickListener {
            item.quantity += 1
            notifyItemChanged(position)
        }

        holder.minusButton.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity -= 1
                notifyItemChanged(position)
            }
        }

        holder.itemView.setOnClickListener { itemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Fruits>) {
        items = newItems
        notifyDataSetChanged()
    }
}