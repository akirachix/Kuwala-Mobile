package com.akirachix.dishhub

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.core.view.WindowInsetsCompat
import com.akirachix.dishhub.databinding.ActivityCategoriesBinding

class Categories : AppCompatActivity() {
    lateinit var  binding :ActivityCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)

        setContentView(binding.root)



        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.recipe -> {
                    loadFragment(RecipesFragment())
                    true
                }
                R.id.pantry -> {
                    loadFragment(PantryFragment())
                    true
                }
                R.id.shop ->{
                    loadFragment(ShopFragment())
                    true
                }

                else -> true

            }

        }

        if(savedInstanceState == null){
            loadFragment(HomeFragment())
            binding.bottomNavigationView.selectedItemId = R.id.home
        }
        fun loadFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction().replace(R.id.fcvHome, fragment).commit()
        }
    }

   private  fun loadFragment(fragment: Fragment){
       supportFragmentManager.beginTransaction().replace(R.id.fcvHome,fragment).commit()
   }
}