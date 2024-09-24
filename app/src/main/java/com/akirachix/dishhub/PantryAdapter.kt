
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.PantryItems
import com.akirachix.dishhub.R

class  PantryAdapter(private var itemsList: List<PantryItems>) :
    RecyclerView.Adapter<PantryAdapter.ItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val foodItem = itemsList[position]
        holder.item.text = foodItem.item
        holder.quantity.text = foodItem.quantity.toString()

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item: TextView = itemView.findViewById(R.id.item)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
    }
}
