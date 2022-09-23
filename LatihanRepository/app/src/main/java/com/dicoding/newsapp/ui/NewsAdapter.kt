package com.dicoding.newsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.newsapp.R
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.databinding.ItemNewsBinding
import com.dicoding.newsapp.ui.NewsAdapter.MyViewHolder
import com.dicoding.newsapp.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) : ListAdapter<NewsEntity, MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        /* kode dipakai untuk menandai apakah berita sudah di tandai atau belom
        * penyimpanan kode ini ditaruh pada bindviewholdeer bukan di viewHolder adalah karena
        * kita ingin menyimpan state dari bookmark yang ditandai. Apabila Anda meletakkan kode ini
        * di ViewHolder, maka state akan ikut ter-recycle (daur ulang) yang menyebabkan tanda
        * tidak semestinya.*/
        if (news.isBookmarked) {
            //jika ada akan menampilkan kode bookmark yang full
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
        } else {
            //jika tidak maka akan menampilkan bookmark yang border saja
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_white))
        }
        ivBookmark.setOnClickListener {
            /* method onBookmarClick digunakan seperti callback unuk melakukan aksi pada kelas yang
            * bukan adapter dan akan memanggil blok kode inisialiasi  adapter di newFragment*/
            onBookmarkClick(news)
        }
    }

    class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: NewsEntity) {
            binding.tvItemTitle.text = news.title
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgPoster)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldUser: NewsEntity, newUser: NewsEntity): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: NewsEntity, newUser: NewsEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}