package com.example.quanlych.admin.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlych.R
import com.example.quanlych.data.DatabaseHelper
import com.example.quanlych.databinding.FragmentAdminProductBinding
import com.example.quanlych.model.Product

class AdminProductFragment : Fragment(), ProductAdapter.OnItemClickListener {

    private var _binding: FragmentAdminProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var productAdapter: ProductAdapter
    private var productList: List<Product> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        loadProducts()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_admin_to_addproduct)
        }
    }

    private fun loadProducts() {
        try {
            productList = databaseHelper.getAllProducts()
            if (productList.isEmpty()) {
                Log.d("AdminProductFragment", "No products found.")
            } else {
                Log.d("AdminProductFragment", "Products loaded successfully. Count: ${productList.size}")
            }
            productAdapter = ProductAdapter(productList, this)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = productAdapter
        } catch (e: Exception) {
            Log.e("AdminProductFragment", "Error loading products", e)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditClick(product: Product) {
        // Handle edit product action
    }

    override fun onDeleteClick(product: Product) {
        try {
            databaseHelper.deleteProduct(product.id.toInt())
            loadProducts() // Reload the product list after deletion
        } catch (e: Exception) {
            Log.e("AdminProductFragment", "Error deleting product", e)
        }
    }
}
