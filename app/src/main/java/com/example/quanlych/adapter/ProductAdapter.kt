package com.example.quanlych.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quanlych.databinding.ItemProductBinding
import com.example.quanlych.model.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val product = productList[position]
                    onItemClickListener.onItemClick(product)
                }
            }
        }

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price.toString()

            // Load image from URL or file path using Glide
            Glide.with(binding.productImage.context)
                .load(product.imageResource) // This should be a URL or file path as a String
                .into(binding.productImage)
        }
    }
}
