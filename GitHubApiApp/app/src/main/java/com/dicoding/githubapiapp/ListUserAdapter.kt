package com.dicoding.githubapiapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapiapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>):
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
        val (username, name, repo, followers, photo) = listUser[position]

        holder.binding.tvUsername.text = username
        holder.binding.tvFullname.text = name
        holder.binding.tvRepositories.text = "$repo Repositories"
        holder.binding.tvFollowers.text = "$followers Followers"
        Glide.with(holder.itemView.context)
            .load(photo)
            .circleCrop()
            .into(holder.binding.imgItem)

        holder.binding.imgItem.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}