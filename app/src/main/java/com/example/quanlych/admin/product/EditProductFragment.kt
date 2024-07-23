package com.example.quanlych.admin.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quanlych.R
import com.example.quanlych.data.DatabaseHelper
import com.example.quanlych.databinding.FragmentEditProductBinding
import com.example.quanlych.model.Product

class EditProductFragment : Fragment() {

    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHelper: DatabaseHelper
    private var productId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        // Get product ID from arguments
        arguments?.let {
            productId = it.getInt("productId", -1)
            if (productId != -1) {
                loadProductDetails(productId)
            }
        }

        binding.saveButton.setOnClickListener {
            saveProduct()
        }
    }

    private fun loadProductDetails(productId: Int) {
        val product = databaseHelper.getProductById(productId)
        product?.let {
            binding.editProductName.setText(it.name)
            binding.editProductDescription.setText(it.description)
            binding.editProductPrice.setText(it.price.toString())
            binding.editProductQuantity.setText(it.quantity.toString())
            binding.editImageUrl.setText(it.imageResource) // Load existing image URL if needed
        }
    }

    private fun saveProduct() {
        val name = binding.editProductName.text.toString()
        val description = binding.editProductDescription.text.toString()
        val price = binding.editProductPrice.text.toString().toDoubleOrNull()
        val quantity = binding.editProductQuantity.text.toString().toIntOrNull()
        val imageUrl = binding.editImageUrl.text.toString()

        if (price != null && quantity != null) {
            val product = Product(
                id = productId,
                name = name,
                description = description,
                imageResource = imageUrl, // Save the image URL or file path
                price = price,
                quantity = quantity
            )

            databaseHelper.updateProduct(product)
            findNavController().popBackStack()
        } else {
            // Handle invalid input if needed
            // For example, you can show a toast or alert dialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}