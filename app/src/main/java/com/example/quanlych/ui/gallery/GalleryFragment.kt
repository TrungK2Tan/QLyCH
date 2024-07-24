package com.example.quanlych.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val databaseHelper = DatabaseHelper(requireContext())
        val products = databaseHelper.getAllProducts()

        val recyclerView = binding.recycleview
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columns
        val adapter = ProductAdapter(products, object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(product: Product) {
                val bundle = Bundle().apply {
                    putParcelable("product", product)
                }
                findNavController().navigate(R.id.action_gallery_to_productdetails, bundle)
            }
        })
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
