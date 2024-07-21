package com.example.quanlych.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quanlych.R

class ProductDetailFragment : Fragment() {

    private var quantity: Int = 1
    private val productPriceValue: Double = 99.99 // Set the product price value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productdetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productDescription: TextView = view.findViewById(R.id.txtmotachitiet)
        val addToCartButton: Button = view.findViewById(R.id.btnthemvaogiohang)
        val btnIncrement: Button = view.findViewById(R.id.btn_increment)
        val btnDecrement: Button = view.findViewById(R.id.btn_decrement)
        val txtQuantity: TextView = view.findViewById(R.id.txt_quantity)
        val txtTotalPrice: TextView = view.findViewById(R.id.txt_total_price)

        // Set product details (example)
        productName.text = "San Pham 1"
        productPrice.text = "${productPriceValue}đ"
        productImage.setImageResource(R.drawable.banner1)
        productDescription.text = "San Pham nay la san pham xin."

        // Set initial total price
        txtTotalPrice.text = "Total: ${productPriceValue * quantity}đ"

        btnIncrement.setOnClickListener {
            quantity++
            txtQuantity.text = quantity.toString()
            txtTotalPrice.text = "Total: ${String.format("%.2f", productPriceValue * quantity)}đ"
        }

        btnDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                txtQuantity.text = quantity.toString()
                txtTotalPrice.text = "Total: ${String.format("%.2f", productPriceValue * quantity)}đ"
            }
        }

        addToCartButton.setOnClickListener {
            // Handle add to cart functionality
            Toast.makeText(context, "Thêm vào giỏ hàng với số lượng $quantity", Toast.LENGTH_SHORT).show()
        }
    }
}
