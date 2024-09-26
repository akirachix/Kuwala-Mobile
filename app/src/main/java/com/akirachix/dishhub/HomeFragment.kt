package com.akirachix.dishhub

import GrainsAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akirachix.dishhub.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addButton = binding.buttonAddItem

        addButton?.setOnClickListener {
            startActivity(Intent(activity, AddItemManually::class.java))

        }

        val fruit = binding.fruits

        fruit?.setOnClickListener {
            startActivity(Intent(activity, FruitsCategory::class.java))
        }

        val dairy = binding.imageView5

        dairy?.setOnClickListener {
            startActivity(Intent(activity, DairyCategory::class.java))
        }

        val vegetables = binding.imageView4
        vegetables?.setOnClickListener {
            startActivity(Intent(activity, VegetablesCategory::class.java))
        }

        val grain = binding.imageView6
        grain?.setOnClickListener {
            startActivity(Intent(activity, GrainsCategory::class.java))
        }

    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types of parameters
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
