package com.example.quanlych.admin.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.databinding.AdminItemProductBinding
import com.example.quanlych.model.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(product: Product)
        fun onDeleteClick(product: Product)
    }

    inner class ProductViewHolder(private val binding: AdminItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            binding.productPrice.text = product.price.toString()
            binding.productQuantity.text = product.quantity.toString()
            binding.productImage.setImageResource(product.imageResource)

            binding.editButton.setOnClickListener { listener.onEditClick(product) }
            binding.deleteButton.setOnClickListener { listener.onDeleteClick(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = AdminItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
