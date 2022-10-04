package com.dicoding.githubapidatabase.models

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapidatabase.MainActivity.Companion.LOGIN
import com.dicoding.githubapidatabase.R
import com.dicoding.githubapidatabase.api.Users
import com.dicoding.githubapidatabase.databinding.ItemRowUserBinding

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

    @SuppressLint("SetTextI18n", "StringFormatMatches")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvUsername.text = user.login
        holder.binding.tvId.text = holder.binding.tvId.context.getString(R.string.viewIdText, user.id)
        holder.binding.tvUrl.text = holder.binding.tvUrl.context.getString(R.string.viewWebText, user.html_url)
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItem)

        LOGIN = user.login
        holder.binding.imgItem.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser .size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}