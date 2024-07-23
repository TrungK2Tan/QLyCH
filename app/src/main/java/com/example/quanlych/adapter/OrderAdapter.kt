package com.example.quanlych.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.databinding.ItemOrderBinding
import com.example.quanlych.model.Order

class OrderAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.binding.txtOrderId.text = "Order ID: ${order.id}"
        holder.binding.txtOrderIdTaiKhoan.text = "TaiKhoan ID: ${order.userId}"
        holder.binding.txtOrderDate.text = "Date: ${order.date}"
        holder.binding.txtOrderTotal.text = "Total: ${order.total}"
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}
