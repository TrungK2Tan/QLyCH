package com.example.quanlych.admin.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quanlych.R
import com.example.quanlych.data.DatabaseHelper
import com.example.quanlych.model.Product

class AddProductFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        val editTextProductName: EditText = view.findViewById(R.id.editTextProductName)
        val editTextProductDescription: EditText = view.findViewById(R.id.editTextProductDescription)
        val editTextProductPrice: EditText = view.findViewById(R.id.editTextProductPrice)
        val buttonSaveProduct: Button = view.findViewById(R.id.buttonSaveProduct)

        buttonSaveProduct.setOnClickListener {
            val name = editTextProductName.text.toString()
            val description = editTextProductDescription.text.toString()
            val price = editTextProductPrice.text.toString().toDoubleOrNull() ?: 0.0

            if (name.isNotBlank() && description.isNotBlank()) {
                val product = Product(
                    id = "0",
                    name = name,
                    description = description,
                    imageResource = R.drawable.banner3,
                    price = price,
                    quantity = 1
                )
                databaseHelper.addProduct(product)
                // Optionally, you can show a message indicating the product has been added
                findNavController().popBackStack()
            } else {
                // Show an error message if fields are not valid
            }
        }
    }
}
