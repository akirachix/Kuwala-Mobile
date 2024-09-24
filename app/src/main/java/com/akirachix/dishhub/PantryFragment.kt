
package com.akirachix.dishhub

import PantryAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.dishhub.databinding.FragmentPantryBinding

@Suppress("UNREACHABLE_CODE")
class PantryFragment : Fragment() {
    private lateinit var binding: FragmentPantryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPantryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        displayItems()
        return binding.root

    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = binding.btnYumAhead
        recipe?.setOnClickListener {
            startActivity(Intent(activity,RecipesFragment ::class.java))
        }

        val fruit = binding.btnYumAhead
        recipe?.setOnClickListener {
            startActivity(Intent(activity, Recipes::class.java))
        }


    }

    private fun setupRecyclerView() {
        binding.rvpantry.layoutManager = LinearLayoutManager(requireContext())
        val pantryAdapter = PantryAdapter(mutableListOf())
        binding.rvpantry.adapter = pantryAdapter
    }




    private fun displayItems() {
        val items = listOf(
            PantryItems("Onion", 1, ""),
            PantryItems("Tomatoes", 3, ""),
            PantryItems("Rice", 2, ""),
            PantryItems("Milk", 6, ""),
            PantryItems("Beef", 5, ""),
            PantryItems("Kales", 4, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Kales", 2, ""),
            PantryItems("Flour", 2, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Mangoes", 2, ""),
            PantryItems("oranges", 2, ""),
            PantryItems("Ovacado", 2, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Pork", 2, ""),
            PantryItems("Pork", 2, ""),

        )

        val pantryAdapter = PantryAdapter(items)
          binding.rvpantry.adapter = pantryAdapter

    }

}



