package com.example.quanlych.ui.Cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlych.R
import com.example.quanlych.adapter.CartAdapter
import com.example.quanlych.databinding.FragmentCartBinding
import com.example.quanlych.model.Product
import com.example.quanlych.utils.CartManager

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cartAdapter = CartAdapter(
            CartManager.cartItems,
            ::onQuantityChanged,
            ::onProductChecked
        )
        binding.recycleviewgiohang.layoutManager = LinearLayoutManager(context)
        binding.recycleviewgiohang.adapter = cartAdapter

        updateTotalPrice()
        checkIfCartIsEmpty()

        binding.btnmuahang.setOnClickListener {
            val totalPrice = calculateTotalPrice()
            val selectedProducts = CartManager.cartItems.filter { it.isSelected }

            val bundle = Bundle().apply {
                putDouble("totalPrice", totalPrice)
                putParcelableArrayList("selectedProducts", ArrayList(selectedProducts))
                putString("userPhoneNumber", "0362922312") // Replace with actual user phone number
                putString("userEmail", "thaotran@gmail.com") // Replace with actual user email
            }

            findNavController().navigate(R.id.action_nav_cart_to_nav_thanhtoan, bundle)
        }
        return root
    }

    private fun onQuantityChanged(product: Product) {
        updateTotalPrice()
    }

    private fun onProductChecked(product: Product, isChecked: Boolean) {
        updateTotalPrice()
    }

    private fun calculateTotalPrice(): Double {
        return CartManager.cartItems.filter { it.isSelected }
            .sumOf { it.quantity * it.price }
    }

    private fun updateTotalPrice() {
        val totalPrice = calculateTotalPrice()
        binding.txttongtien.text = String.format("%.2fđ", totalPrice)
        checkIfCartIsEmpty()
    }

    private fun checkIfCartIsEmpty() {
        if (CartManager.cartItems.isEmpty()) {
            binding.recycleviewgiohang.visibility = View.GONE
            binding.textCart.visibility = View.VISIBLE
            binding.txttongtien.visibility = View.GONE
            binding.btnmuahang.visibility = View.GONE
        } else {
            binding.recycleviewgiohang.visibility = View.VISIBLE
            binding.textCart.visibility = View.GONE
            binding.txttongtien.visibility = View.VISIBLE
            binding.btnmuahang.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
