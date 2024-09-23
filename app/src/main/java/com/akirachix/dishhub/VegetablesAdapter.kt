package com.akirachix.dishhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VegetablesAdapter(var vegetablesList: List<Vegetables>):
    RecyclerView.Adapter<VegetablesViewHolder> (){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VegetablesViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_vegetables,parent,false)
            return VegetablesViewHolder(itemView)

        }

        override fun onBindViewHolder(holder: VegetablesViewHolder, position: Int) {
            val myVegetables = vegetablesList[position]
            holder.etFoodName.text = myVegetables.name
            holder.etQuantity.text = myVegetables.quantity.toString()


        }

        override fun getItemCount(): Int {
            return vegetablesList.size

        }

    }

    class VegetablesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var etFoodName =itemView.findViewById<TextView>(R.id.etFoodName)
        var etQuantity =itemView.findViewById<TextView>(R.id.etQuantity)

    }
