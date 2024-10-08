import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.Dairy
import com.akirachix.dishhub.R

class DairyAdapter(
    private var items: List<Dairy>,
    private val itemClick: (Dairy) -> Unit
) : RecyclerView.Adapter<DairyAdapter.DairyViewHolder>() {

    class DairyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
        val plusButton: ImageView = view.findViewById(R.id.btnpantrytoprofile)
        val minusButton: ImageView = view.findViewById(R.id.imageView16)
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

    fun updateItems(newItems: List<Dairy>) {
        items = newItems
        notifyDataSetChanged()
    }
}









