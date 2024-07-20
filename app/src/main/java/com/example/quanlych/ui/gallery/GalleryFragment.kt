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
import com.example.quanlych.databinding.FragmentGalleryBinding
import com.example.quanlych.model.Product
import com.example.quanlych.ui.home.ProductAdapter

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
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
            Product(R.drawable.banner1, "Product 1", "10.000đ", 1),
            Product(R.drawable.banner2, "Product 2", "20.000đ", 1),
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