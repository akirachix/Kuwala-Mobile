//
//
//
//
//
//
//package com.akirachix.dishhub
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.akirachix.dishhub.databinding.FragmentRecipesBinding
//
//class RecipesFragment : Fragment() {
//    private var _binding: FragmentRecipesBinding? = null
//    private val binding get() = _binding!!
//
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val recipe = binding.beefRecipe  // Ensure this view is defined in the XML with the correct ID
//        recipe?.setOnClickListener {
//            startActivity(Intent(activity, RecipeDisplay::class.java))  // Start the RecipeDisplay Activity
//        }
//    }
//
//    companion object {
//        private const val ARG_PARAM1 = "param1"
//        private const val ARG_PARAM2 = "param2"
//
//        fun newInstance(param1: String, param2: String) =
//            RecipesFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}