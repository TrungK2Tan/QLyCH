package com.example.quanlych.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quanlych.R
import com.example.quanlych.databinding.FragmentHomeBinding
import com.example.quanlych.model.Product

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewFlipper: ViewFlipper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Accessing ViewFlipper
        viewFlipper = binding.viewFlipper
        viewFlipper.startFlipping()

        // Set up TextView from ViewModel
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up RecyclerView with GridLayoutManager
        val recyclerView = binding.recycleview
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columns

        // Create a sample product list
        val products = listOf(
            Product(R.drawable.banner1, "Product 1", "10.000đ", 1),
            Product(R.drawable.banner2, "Product 2", "20.000đ", 1),
            Product(R.drawable.banner3, "Product 3", "30.000đ", 1),
            Product(R.drawable.banner3, "Product 3", "30.000đ", 1)
        )

        val adapter = ProductAdapter(products)
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
