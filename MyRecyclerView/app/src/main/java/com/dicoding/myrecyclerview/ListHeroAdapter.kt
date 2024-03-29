package com.dicoding.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myrecyclerview.databinding.ItemRowHeroBinding

class ListHeroAdapter(private val listHero: ArrayList<Hero>): RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    /*
    * Kelas ListViewHolder digunakan sebagai ViewHolder dalam RecyclerView. ViewHolder adalah wrapper
    * seperti VIew yang berisi layout untuk setiap item dalam daftar RecyclerView. Di sinilah tempat
    * untuk menginisialisasi setiap komponen pada layout item dengan menggunakan itemView.findViewById.
    *
    * Kemudian, hubungan antara satu adapter dengan ViewHolder adalah satu ke banyak. Artinya,
    * satu kelas adapter bisa memiliki lebih dari satu ViewHolder.*/
//    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        //identifikasi properti pada activity item_row_hero
//        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
//        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
//    }

    //menggunakan viewBinding
    class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)

    //variabel menyimpan item yang di click, untuk diimplementasikan ke main
    private lateinit var onItemClickCallback: OnItemClickCallback

    //fungsi menyimpan item yang di clik (implementasi ke main)
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    /*
    * Fungsi ini digunakan untuk membuat viewHolder baru yang berisi layout item yang digunakan
    * dalam hal ini yaitu R.layout.item_row_hero. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        //val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        //penggunakan menggunakan viewBinding
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    /*
    * Fungsi onBindViewHolder() digunakan untuk menetapkan data yang ada ke dalam ViewHolder sesuai
    * dengan posisinya dengan menggunakan listHero[position]*/
    //pada fungsi ini kita dapat menambahkan fungsi onClick()
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listHero[position]
        //menyimpan data foto, nama, dan desc sesuai widget yang telah ditentukan
        //holder.imgPhoto.setImageResource(photo)       //jika foto diambil dari lokal / berupa int

        //jika foto diambild dari url, maka perlu menggunkan glide
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.binding.imgItemPhoto) // imageView mana yang akan diterapkan
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDescription.text = description

        //fungsi onClick pada recycler view jika ingin mengimplentasi onclic  pada main activity
        //activity akan berpindah ketika user menekan objek apapun yang ada pada container list
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHero[holder.adapterPosition]) }

        /*
        * Method jika fungsi onClick tidak ingin di implementasikan ke main, maka tidak perlu menggunakan
        * interface
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Kamu memilih " + listHero[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
        }*/
    }

    //fungsi ini digunakan untuk menetapkan ukuran list data yang ingin ditampilkan
    override fun getItemCount(): Int = listHero.size        //menggunakan kode listHero.size jika ingin menampilkan semua data

    //interface untuk mengimplentasi onclick pada main activity
    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }
}