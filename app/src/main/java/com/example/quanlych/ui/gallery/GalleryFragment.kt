package com.example.quanlych.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quanlych.R
import com.example.quanlych.data.DatabaseHelper
import com.example.quanlych.databinding.FragmentGalleryBinding
import com.example.quanlych.model.Product
import com.example.quanlych.ui.home.ProductAdapter

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up TextView from ViewModel
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Initialize DatabaseHelper and fetch products
        val databaseHelper = DatabaseHelper(requireContext())
        val products = databaseHelper.getAllProducts()

        // Set up RecyclerView with GridLayoutManager
        val recyclerView = binding.recycleview
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columns
        val adapter = ProductAdapter(products)
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
