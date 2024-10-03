//package com.akirachix.dishhub
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//
//class RecipeAdapter(private val recipeList: List<Recipe>) :
//    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_list, parent, false)
//        return RecipeViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
//        val recipe = recipeList[position]
//
//        // Bind recipe name, description, and image
//        holder.recipeName.text = recipe.title
//
//
//        // Load the image using Glide or any image loading library
//        Glide
//            .with(holder.itemView.context)
//            .load(recipe.imageUrl)  // Assuming Recipe has imageUrl field
//            .into(holder.recipeImage)
//    }
//
//    override fun getItemCount(): Int = recipeList.size
//
//    // ViewHolder for individual recipe item
//    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val recipeName: TextView = itemView.findViewById(R.id.recipe)
//        val recipeImage: ImageView = itemView.findViewById(R.id.imageView8)
//    }
//}

//package com.akirachix.dishhub
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.akirachix.dishhub.model.Recipe
//
//class RecipeAdapter(private var recipes: ArrayList<Recipe> = ArrayList()) : // Default empty list
//    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
//        // Inflate the layout for each recipe item
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false) // Change to the correct layout name
//        return RecipeViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
//        val recipe = recipes[position]
//        holder.title.text = recipe.name                // Set the recipe name
//        holder.instructions.text = recipe.instructions   // Set the recipe instructions
//    }
//
//    override fun getItemCount(): Int = recipes.size // Return the number of recipes
//
//    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val title: TextView = itemView.findViewById(R.id.idtitle)            // Ensure this ID matches your item layout
//        val instructions: TextView = itemView.findViewById(R.id.idInstructions)  // Ensure this ID matches your item layout
//    }
//}





package com.akirachix.dishhub

import RecipeDetailsDisplay
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.databinding.ActivityRecipeItemBinding

class RecipeAdapter(private val recipes: List<RecipeDetailsDisplay>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

//    class RecipeViewHolder(private val binding: ActivityRecipeItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(recipe: RecipeDetailsDisplay) {
//            binding.apply {
//                name.text = recipe.name
//                // Convert ingredients list to string safely
//                ingredients.text = if (recipe.ingredients.isNotEmpty()) {
//                    recipe.ingredients.toString()
//                        .removeSurrounding("[", "]") // Remove square brackets
//                } else {
//                    "No ingredients available"
//                }
//                instructions.text = recipe.instructions
//            }
//        }
//    }


    class RecipeViewHolder(private val binding: ActivityRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeDetailsDisplay) {
            binding.apply {
                name.text = recipe.name
                ingredients.text = if (recipe.ingredients.isNotEmpty()) {
                    recipe.ingredients.toString().removeSurrounding("[", "]")
                } else {
                    "No ingredients available"
                }
                instructions.text = recipe.instructions
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ActivityRecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size
}