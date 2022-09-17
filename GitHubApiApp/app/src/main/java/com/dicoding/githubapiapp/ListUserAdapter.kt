package com.dicoding.githubapiapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapiapp.databinding.ItemRowUserBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class ListUserAdapter(private val listUser: List<Users>):
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvUsername.text = user.login
        holder.binding.tvId.text = "ID : ${user.id}"
        holder.binding.tvUrl.text = "Web : ${user.html_url}"
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItem)

        holder.binding.imgItem.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser .size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}