








package com.akirachix.dishhub

import RecipeDetailsDisplay
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.dishhub.databinding.ActivityRecipeItemBinding

class RecipeAdapter(private val recipes: List<RecipeDetailsDisplay>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ActivityRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeDetailsDisplay) {
            binding.apply {
                var isExpanded = false
                expandableContent.visibility = View.GONE
                expandIcon.setImageResource(R.drawable.ic_expand_more)

                recipeCard.setOnClickListener {
                    isExpanded = !isExpanded
                    expandableContent.visibility = if (isExpanded) View.VISIBLE else View.GONE
                    expandIcon.setImageResource(if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
                }

                recipeName.text = recipe.name
                recipeInstructions.text = "Click here for full recipe instructions"
                recipeInstructions.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.url))
                    itemView.context.startActivity(intent)
                }

                // Optional: Load an image using a library like Glide or Picasso
                // e.g., Glide.with(itemView.context).load(recipe.image).into(recipeImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size
}
