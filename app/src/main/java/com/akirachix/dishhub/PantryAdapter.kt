



import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.PantryItems
import com.akirachix.dishhub.R





// PantryAdapter.kt
class PantryAdapter(
    private val pantryItems: List<PantryItems>,
    private val selectedIngredients: MutableList<String>
) : RecyclerView.Adapter<PantryAdapter.ViewHolder>() {

    private var filteredItems = pantryItems.toList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val itemName: TextView = itemView.findViewById(R.id.item)
        private val quantityText: TextView = itemView.findViewById(R.id.quantity)
        private val minusButton: ImageView = itemView.findViewById(R.id.negative)

        fun bind(ingredient: PantryItems) {
            itemName.text = ingredient.name
            quantityText.text = ingredient.quantity.toString()
            checkbox.isChecked = selectedIngredients.contains(ingredient.name)

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedIngredients.contains(ingredient.name)) {
                        selectedIngredients.add(ingredient.name)
                    }
                } else {
                    selectedIngredients.remove(ingredient.name)
                }
            }

            minusButton.setOnClickListener {
                // Handle quantity decrease if needed
                val currentQuantity = ingredient.quantity
                if (currentQuantity > 1) {
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

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            pantryItems
        } else {
            pantryItems.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    private fun savePantryItems() {
        // Implement saving updated quantities to SharedPreferences if needed
    }
}