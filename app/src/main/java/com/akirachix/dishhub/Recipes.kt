package com.akirachix.dishhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.akirachix.dishhub.databinding.ActivityLoginBinding

class Recipes: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_display)
        setContentView(R.layout.fragment_recipes)
    }

}