package com.example.quanlych.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.databinding.ItemCartBinding
import com.example.quanlych.model.Product

class CartAdapter(
    private val products: List<Product>,
    private val onQuantityChanged: (Product) -> Unit,
    private val onProductChecked: (Product, Boolean) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = "${product.price}đ"
            binding.txtQuantity.text = product.quantity.toString()

            binding.btnIncrement.setOnClickListener {
                product.quantity++
                binding.txtQuantity.text = product.quantity.toString()
                onQuantityChanged(product)
            }

            binding.btnDecrement.setOnClickListener {
                if (product.quantity > 1) {
                    product.quantity--
                    binding.txtQuantity.text = product.quantity.toString()
                    onQuantityChanged(product)
                }
            }

            binding.checkboxSelectProduct.isChecked = product.isSelected
            binding.checkboxSelectProduct.setOnCheckedChangeListener { _, isChecked ->
                product.isSelected = isChecked
                onProductChecked(product, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
