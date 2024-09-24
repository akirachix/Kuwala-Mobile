package com.akirachix.dishhub


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GrainsAdapter(var grainsList: List<Grains>):
    RecyclerView.Adapter<GrainsViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrainsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_grains,parent,false)
        return GrainsViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: GrainsViewHolder, position: Int) {
        val myGrains = grainsList[position]
        holder.etFoodName.text = myGrains.name
        holder.etQuantity.text = myGrains.quantity.toString()


    }

    override fun getItemCount(): Int {
        return grainsList.size

    }

}

class GrainsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var etFoodName =itemView.findViewById<TextView>(R.id.etFoodName)
    var etQuantity =itemView.findViewById<TextView>(R.id.etQuantity)

}

