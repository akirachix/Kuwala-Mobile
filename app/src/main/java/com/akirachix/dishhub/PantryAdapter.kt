

//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CheckBox
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.akirachix.dishhub.PantryItems
//import com.akirachix.dishhub.R
//
//class PantryAdapter(
//    private var pantryItems: MutableList<PantryItems>,
//    private val selectedIngredients: MutableList<String>,
//    private val onPantryItemChange: () -> Unit
//) : RecyclerView.Adapter<PantryAdapter.ViewHolder>() {
//
//    private var filteredItems = mutableListOf<PantryItems>()
//
//    init {
//        filteredItems = pantryItems.toMutableList()
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
//        private val itemName: TextView = itemView.findViewById(R.id.item)
//        private val quantityText: TextView = itemView.findViewById(R.id.quantity)
//        private val minusButton: ImageView = itemView.findViewById(R.id.negative)
//
//        fun bind(ingredient: PantryItems) {
//            itemName.text = ingredient.item
//            quantityText.text = ingredient.quantity
//
//            checkbox.setOnCheckedChangeListener(null)
//            checkbox.isChecked = selectedIngredients.contains(ingredient.item)
//
//            checkbox.setOnCheckedChangeListener { _, isChecked ->
//                if (isChecked) {
//                    if (!selectedIngredients.contains(ingredient.item)) {
//                        selectedIngredients.add(ingredient.item.toString())
//                    }
//                } else {
//                    selectedIngredients.remove(ingredient.item)
//                }
//                onPantryItemChange()
//            }
//
//            minusButton.setOnClickListener {
//                val currentQuantity = ingredient.quantity.toInt()
//                if (currentQuantity > 1) {
//                    ingredient.quantity = (currentQuantity - 1).toString()
//                    notifyItemChanged(adapterPosition)
//                    onPantryItemChange()
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_list, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(filteredItems[position])
//    }
//
//    override fun getItemCount() = filteredItems.size
//
//    fun updateItems(newItems: List<PantryItems>) {
//        pantryItems.clear()
//        pantryItems.addAll(newItems)
//        filter("")  // Reset filter
//    }
//
//    fun getPantryItems(): List<PantryItems> = pantryItems.toList()
//
//    fun filter(query: String) {
//        filteredItems = if (query.isEmpty()) {
//            pantryItems.toMutableList()
//        } else {
//            pantryItems.filter {
//                it.item.contains(query, ignoreCase = true)
//            }.toMutableList()
//        }
//        notifyDataSetChanged()
//    }
//}










import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.PantryItems
import com.akirachix.dishhub.R




class PantryAdapter(
    private var pantryItems: MutableList<PantryItems>,  // Use MutableList to allow modifications
    private val selectedIngredients: MutableList<String>,
    private val onPantryItemChange: () -> Unit
) : RecyclerView.Adapter<PantryAdapter.ViewHolder>() {

    private var filteredItems = pantryItems.toList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val itemName: TextView = itemView.findViewById(R.id.item)
        private val quantityText: TextView = itemView.findViewById(R.id.quantity)
        private val minusButton: ImageView = itemView.findViewById(R.id.negative)

        fun bind(ingredient: PantryItems) {
            itemName.text = ingredient.item
            quantityText.text = ingredient.quantity.toString()
            checkbox.isChecked = selectedIngredients.contains(ingredient.item)

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedIngredients.add(ingredient.item)
                } else {
                    selectedIngredients.remove(ingredient.item)
                }
            }

            minusButton.setOnClickListener {
                val currentQuantity = ingredient.quantity
                if (currentQuantity > 1.toString()) {
                    ingredient.quantity = currentQuantity - 1
                    quantityText.text = ingredient.quantity.toString()
                    savePantryItems()
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

    // Method to update pantry items
    fun updateItems(newItems: List<PantryItems>) {
        pantryItems.clear()
        pantryItems.addAll(newItems)
        filteredItems = pantryItems.toList()
        notifyDataSetChanged()
    }

    // Method to retrieve the current list of pantry items
    fun getPantryItems(): List<PantryItems> {
        return pantryItems
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            pantryItems.toList()  // Reset to original pantry items
        } else {
            pantryItems.filter { it.item.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    private fun savePantryItems() {
        // Implement saving updated quantities to SharedPreferences if needed
    }
}

private operator fun String.minus(i: Int): String {
    TODO("Not yet implemented")
}














