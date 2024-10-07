



//package com.akirachix.dishhub
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CheckBox
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class GrainsAdapter(
//    private var items: List<Grains>,
//    private val itemClick: (Grains) -> Unit
//) : RecyclerView.Adapter<GrainsAdapter.GrainsViewHolder>() {
//
//    class GrainsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
//        val foodName: TextView = view.findViewById(R.id.etFoodName)
//        val quantity: TextView = view.findViewById(R.id.etQuantity)
//        val plusButton: ImageView = view.findViewById(R.id.imageView10)
//        val minusButton: ImageView = view.findViewById(R.id.imageView16)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrainsViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.list_item_grains, parent, false)
//        return GrainsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: GrainsViewHolder, position: Int) {
//        val item = items[position]
//        holder.foodName.text = item.name
//        holder.quantity.text = item.quantity.toString()
//        holder.checkBox.isChecked = item.isSelected
//
//        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
//            item.isSelected = isChecked
//        }
//
//        holder.plusButton.setOnClickListener {
//            item.quantity++
//            holder.quantity.text = item.quantity.toString()
//        }
//
//        holder.minusButton.setOnClickListener {
//            if (item.quantity > 0) {
//                item.quantity--
//                holder.quantity.text = item.quantity.toString()
//            }
//        }
//
//        holder.itemView.setOnClickListener { itemClick(item) }
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    fun updateItems(newItems: List<Grains>) {
//        items = newItems
//        notifyDataSetChanged()
//    }
//
//    // This method returns a list of selected items as PantryItems objects
//    fun getSelectedItems(): List<PantryItems> {
//        return items.filter { it.isSelected }
//            .map { PantryItems(0, it.name, it.quantity,it.category) }
//    }
//}
//
//
//



package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

private val Nothing?.name: String
    get() {
        TODO("Not yet implemented")
    }

class GrainsAdapter(
    private var items: List<Grains>,
    private val itemClick: (Grains) -> Unit
) : RecyclerView.Adapter<GrainsAdapter.GrainsViewHolder>() {

    class GrainsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
        val plusButton: ImageView = view.findViewById(R.id.imageView10)
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

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
        }

        holder.plusButton.setOnClickListener {
            item.quantity++
            holder.quantity.text = item.quantity.toString()
        }

        holder.minusButton.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                holder.quantity.text = item.quantity.toString()
            }
        }

        holder.itemView.setOnClickListener { itemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Grains>) {
        items = newItems
        notifyDataSetChanged()
    }

    // This method returns a list of selected items as PantryItems objects without category
    fun getSelectedItems(): List<PantryItems> {
        return items.filter { it.isSelected }
            .map {
                val foodName = null
                val vegetable = null
                PantryItems(0, it.name, it.quantity, vegetable.name, foodName.toString())
            } // Create PantryItems without category
    }

    private fun PantryItems(
        id: Int,
        name: String,
        quantity: Int,
        category: String,
        toString: String
    ): PantryItems {
        TODO("Not yet implemented")
    }
}