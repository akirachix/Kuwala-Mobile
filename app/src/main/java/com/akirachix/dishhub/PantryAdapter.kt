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
    private var pantryItems: MutableList<PantryItems>,
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
                onPantryItemChange() // Call the change listener
            }

            minusButton.setOnClickListener {
                val currentQuantity = ingredient.quantity.toInt() // Assuming quantity is a String
                if (currentQuantity > 1) {
                    ingredient.quantity = (currentQuantity - 1).toString()
                    quantityText.text = ingredient.quantity
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

    fun updateItems(newItems: List<PantryItems>) {
        pantryItems.clear()
        pantryItems.addAll(newItems)

        filteredItems = pantryItems.toList()
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

    }
}


