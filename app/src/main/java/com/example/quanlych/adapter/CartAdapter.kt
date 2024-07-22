package com.example.quanlych.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.R
import com.example.quanlych.model.Product

class CartAdapter(
    private val productList: List<Product>,
    private val onQuantityChanged: (Product) -> Unit,
    private val onProductChecked: (Product, Boolean) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val txtQuantity: TextView = itemView.findViewById(R.id.txt_quantity)
        val btnIncrement: Button = itemView.findViewById(R.id.btn_increment)
        val btnDecrement: Button = itemView.findViewById(R.id.btn_decrement)
        val checkboxSelectProduct: CheckBox = itemView.findViewById(R.id.checkbox_select_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = productList[position]
        holder.productImage.setImageResource(product.imageResource)
        holder.productName.text = product.name
        holder.productPrice.text = product.price.toString()
        holder.txtQuantity.text = product.quantity.toString()
        holder.checkboxSelectProduct.isChecked = product.isSelected

        holder.btnIncrement.setOnClickListener {
            product.quantity++
            holder.txtQuantity.text = product.quantity.toString()
            onQuantityChanged(product)
        }

        holder.btnDecrement.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity--
                holder.txtQuantity.text = product.quantity.toString()
                onQuantityChanged(product)
            }
        }

        holder.checkboxSelectProduct.setOnCheckedChangeListener { _, isChecked ->
            product.isSelected = isChecked
            onProductChecked(product, isChecked)
        }
    }

    override fun getItemCount(): Int = productList.size
}
