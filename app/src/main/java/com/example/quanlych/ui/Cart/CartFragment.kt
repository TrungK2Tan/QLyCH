package com.example.quanlych.ui.Cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlych.R
import com.example.quanlych.adapter.CartAdapter
import com.example.quanlych.databinding.FragmentCartBinding
import com.example.quanlych.model.Product

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private lateinit var productList: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCart
        cartViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Initialize product list
        productList = listOf(
            Product(R.drawable.banner1, "Product 1", "10.000đ", 1),
            Product(R.drawable.banner2, "Product 2", "20.000đ", 1),
            Product(R.drawable.banner3, "Product 3", "30.000đ", 1)
        )

        // Set up RecyclerView
        cartAdapter = CartAdapter(productList, ::onQuantityChanged, ::onProductChecked)
        binding.recycleviewgiohang.layoutManager = LinearLayoutManager(context)
        binding.recycleviewgiohang.adapter = cartAdapter

        updateTotalPrice()
// Set up click listener for login button
        binding.btnmuahang.setOnClickListener {
            // Navigate to the Home Fragment on login button click
            findNavController().navigate(R.id.action_nav_cart_to_nav_thanhtoan)
        }
        return root
    }

    private fun onQuantityChanged(product: Product) {
        updateTotalPrice()
    }

    private fun onProductChecked(product: Product, isChecked: Boolean) {
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        var totalPrice = 0
        for (product in productList) {
            if (product.isSelected) {
                totalPrice += product.quantity * product.price.removeSuffix("đ").replace(".", "").toInt()
            }
        }
        binding.txttongtien.text = "${totalPrice}đ"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
