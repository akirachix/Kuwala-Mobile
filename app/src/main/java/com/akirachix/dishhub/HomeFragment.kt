
package com.akirachix.dishhub;

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akirachix.dishhub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileImageView = binding.btnprofile
        profileImageView.setOnClickListener {
            startActivity(Intent(requireContext(), com.akirachix.dishhub.Profile::class.java))
        }
        val addButton = binding.buttonAddItem
        addButton.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AddItemManually::class.java))
            }
        }

        binding.fruits.setOnClickListener {
            startActivity(Intent(activity, FruitsCategory::class.java))
        }

        binding.imageView5.setOnClickListener {
            startActivity(Intent(activity, DairyCategory::class.java))
        }

        binding.imageView4.setOnClickListener {
            startActivity(Intent(activity, VegetablesCategory::class.java))
        }

        binding.imageView6.setOnClickListener {
            startActivity(Intent(activity, GrainsCategory::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}