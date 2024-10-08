import com.akirachix.dishhub.R


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VegetablesAdapter(
    private var items: List<Vegetables>,
    private val itemClick: (Vegetables) -> Unit
) : RecyclerView.Adapter<VegetablesAdapter.VegetablesViewHolder>() {

    class VegetablesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
        val plusButton: ImageView = view.findViewById(R.id.btnpantrytoprofile)
        val minusButton: ImageView = view.findViewById(R.id.imageView16)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VegetablesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_vegetables, parent, false)
        return VegetablesViewHolder(view)
    }

    override fun onBindViewHolder(holder: VegetablesViewHolder, position: Int) {
        val item = items[position]
        holder.foodName.text = item.name
        holder.quantity.text = item.quantity.toString()

        // Set the initial checked state
        holder.checkBox.isChecked = item.isSelected

        // Update item state when checkbox is clicked
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            itemClick(item) // Notify the activity or fragment
        }

        // Handle increment and decrement of quantity
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

        // Optional: handle entire item click if needed
        holder.itemView.setOnClickListener { itemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Vegetables>) {
        items = newItems
        notifyDataSetChanged()
    }
}


