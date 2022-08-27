package com.dicoding.myrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivityHero : AppCompatActivity() {
    private lateinit var fieldDetail: TextView
    private lateinit var fieldImg: ImageView
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_hero)

        fieldImg = findViewById(R.id.detail_img)
        fieldDetail = findViewById(R.id.field_detail_hero)
        val detailHero = intent.getParcelableExtra<Hero>("DATA") as Hero

        val img = detailHero.photo
        val text = """
            Nama        : ${detailHero.name}
            Deskripsi   : ${detailHero.description} 
        """.trimIndent()

        //menampilkan gambar
        fieldImg.setImageResource(img)
        fieldDetail.text = text
    }
}