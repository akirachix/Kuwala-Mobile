package com.akirachix.dishhub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lateinit var bottomNavigationView: BottomNavigationView
        lateinit var home: home
        lateinit var pantry: pantry
        lateinit var recipes: recipes
        lateinit var shop: shop

        home = home()
       pantry = pantry()
       recipes = recipes()
       shop = shop()

        bottomNavigationView = findViewById(R.id.btmNavBar)
        loadFragment(home)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(home)
                    true
                }
                R.id.pantry-> {
                    loadFragment(pantry)
                    true
                }
                R.id.recipes -> {
                    loadFragment(recipes)
                    true
                }

                R.id.shop -> {
                    loadFragment(shop)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.btmNavBar, fragment)
            transaction.commit()
        }
    }
}
