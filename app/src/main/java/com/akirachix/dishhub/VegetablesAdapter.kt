//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.akirachix.dishhub.Vegetables
//
//class VegetablesAdapter(
//    private var items: List<Vegetables>,
//    private val itemClick: (Vegetables) -> Unit
//) : RecyclerView.Adapter<VegetablesAdapter.VegetablesViewHolder>() {
//
//    class VegetablesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView = view.findViewById(android.R.id.text1)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VegetablesViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(android.R.layout.simple_list_item_1, parent, false)
//        return VegetablesViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: VegetablesViewHolder, position: Int) {
//        val item = items[position]
//        holder.textView.text = item.name
//        holder.view.setOnClickListener { itemClick(item) }
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    fun updateItems(newItems: List<Vegetables>) {
//        items = newItems
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
import com.akirachix.dishhub.R
import com.akirachix.dishhub.Vegetables

class VegetablesAdapter(
    private var items: List<Vegetables>,
    private val itemClick: (Vegetables) -> Unit
) : RecyclerView.Adapter<VegetablesAdapter.VegetablesViewHolder>() {

    class VegetablesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.customCheckBox)
        val foodName: TextView = view.findViewById(R.id.etFoodName)
        val quantity: TextView = view.findViewById(R.id.etQuantity)
        val plusButton: ImageView = view.findViewById(R.id.imageView10)
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

    fun updateItems(newItems: List<Vegetables>) {
        items = newItems
        notifyDataSetChanged()
    }
}