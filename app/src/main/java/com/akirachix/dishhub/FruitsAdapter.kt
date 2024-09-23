package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FruitsAdapter(var fruitsList: List<Fruits>):
    RecyclerView.Adapter<FruitsViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_fruits,parent,false)
        return FruitsViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FruitsViewHolder, position: Int) {
        val myFruits = fruitsList[position]
        holder.etFoodName.text = myFruits.name
        holder.etQuantity.text = myFruits.quantity.toString()


    }

    override fun getItemCount(): Int {
        return fruitsList.size

    }

}

class FruitsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var etFoodName =itemView.findViewById<TextView>(R.id.etFoodName)
    var etQuantity =itemView.findViewById<TextView>(R.id.etQuantity)

}
