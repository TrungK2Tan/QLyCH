package com.example.quanlych.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlych.R
import com.example.quanlych.data.User

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val email: TextView = itemView.findViewById(R.id.email)
        val userImage: ImageView = itemView.findViewById(R.id.UserImage)
        val imgDetails: ImageView = itemView.findViewById(R.id.imgdetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.username.text = currentItem.name
        holder.email.text = currentItem.email
        // Set default user image
        holder.userImage.setImageResource(R.drawable.people)
        // Set details image
        holder.imgDetails.setImageResource(R.drawable.details)
    }

    override fun getItemCount() = userList.size
}