package com.dicoding.githubapidatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapidatabase.R
import com.dicoding.githubapidatabase.data.local.UsersEntity
import com.dicoding.githubapidatabase.databinding.ItemRowUserBinding

class ListFavoriteAdapter(private val listUser: List<UsersEntity>):
    RecyclerView.Adapter<ListFavoriteAdapter.ListFavViewHolder>() {

    class ListFavViewHolder(var binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFavViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "StringFormatMatches")
    override fun onBindViewHolder(holder: ListFavViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvUsername.text = user.login
        holder.binding.tvId.text = holder.binding.tvId.context.getString(R.string.viewIdText, user.gitHubId)
        holder.binding.tvUrl.text = holder.binding.tvUrl.context.getString(R.string.viewWebText, user.html_url)
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItem)

        holder.binding.cardView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersEntity)
    }
}