package com.dicoding.githubapiapp.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapiapp.api.FollsResponseItem
import com.dicoding.githubapiapp.databinding.ItemRowUserBinding

class UserFollsAdapter(private val listUserFolls: List<FollsResponseItem>):
    RecyclerView.Adapter<UserFollsAdapter.ListFollsHolder>() {

    class ListFollsHolder(var follsBinding: ItemRowUserBinding): RecyclerView.ViewHolder(follsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollsHolder {
        val follsBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFollsHolder(follsBinding)
    }

    override fun onBindViewHolder(holder: ListFollsHolder, position: Int) {
        val listFolls = listUserFolls[position]
        Glide.with(holder.itemView.context)
            .load(listFolls.avatarUrl)
            .circleCrop()
            .into(holder.follsBinding.imgItem)
        holder.follsBinding.tvUsername.text = listFolls.login
        holder.follsBinding.tvId.text = "ID : ${listFolls.id}"
        holder.follsBinding.tvUrl.text = "Web : ${listFolls.htmlUrl}"

    }

    override fun getItemCount() : Int = listUserFolls.size
}