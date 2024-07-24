package com.example.quanlych.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.R
import com.example.quanlych.databinding.ItemOrderBinding
import com.example.quanlych.model.Order
import java.text.NumberFormat

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrderId: TextView = itemView.findViewById(R.id.txtOrderId)
        val txtOrderDate: TextView = itemView.findViewById(R.id.txtOrderDate)
        val txtOrderTotal: TextView = itemView.findViewById(R.id.txtOrderTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.txtOrderId.text = order.id.toString()
        holder.txtOrderDate.text = order.date
        holder.txtOrderTotal.text = NumberFormat.getInstance().format(order.total) + " đ"
    }

    override fun getItemCount(): Int = orders.size
}

