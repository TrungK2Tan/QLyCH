package com.example.quanlych.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quanlych.R
import com.example.quanlych.model.OrderDetail
import java.text.NumberFormat

class OrderDetailAdapter(private val orderDetails: List<OrderDetail>) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    inner class OrderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        private val txtProductQuantity: TextView = itemView.findViewById(R.id.txtProductQuantity)
        private val txtProductPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
        private val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        fun bind(orderDetail: OrderDetail) {
            txtProductName.text = orderDetail.productName
            txtProductQuantity.text = orderDetail.quantity.toString()
            txtProductPrice.text = NumberFormat.getInstance().format(orderDetail.price) + " đ"
            Glide.with(itemView.context)  // Sử dụng itemView.context thay vì context
                .load(orderDetail.productImage)  // Đảm bảo tên trường khớp với lớp OrderDetail
                .into(imgProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(orderDetails[position])
    }

    override fun getItemCount(): Int = orderDetails.size
}
