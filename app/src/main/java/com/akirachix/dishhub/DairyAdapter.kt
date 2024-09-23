package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DairyAdapter(var dairyList: List<Dairy>):
    RecyclerView.Adapter<DairyViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DairyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_dairy,parent,false)
        return DairyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: DairyViewHolder, position: Int) {
        val myDairy = dairyList[position]
        holder.etFoodName.text = myDairy.name
        holder.etQuantity.text = myDairy.quantity.toString()


    }

    override fun getItemCount(): Int {
        return dairyList.size

    }

}

class DairyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var etFoodName =itemView.findViewById<TextView>(R.id.etFoodName)
    var etQuantity =itemView.findViewById<TextView>(R.id.etQuantity)

}
