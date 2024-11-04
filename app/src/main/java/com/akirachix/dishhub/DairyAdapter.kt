


package com.akirachix.dishhub


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DairyAdapter(
    private var items: List<Dairy>,
    private val itemClick: (Dairy) -> Unit,
    nothing: Nothing?
) : RecyclerView.Adapter<DairyAdapter.DairyViewHolder>() {


    val currentList: List<Dairy>
        get() {
            return items // Assuming 'items' is the list of Dairy items in the adapter
        }




    class DairyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DairyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_dairy, parent, false)
        return DairyViewHolder(view)
    }


    override fun onBindViewHolder(holder: DairyViewHolder, position: Int) {
        val item = items[position]
        holder.foodName.text = item.name
        holder.quantity.text = item.quantity.toString()


        holder.checkBox.isChecked = item.isSelected
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            itemClick(item)
        }
    }


    override fun getItemCount(): Int = items.size


    fun updateItems(newItems: List<Dairy>) {
        items = newItems
        notifyDataSetChanged()
    }
}




