package com.dicoding.myrecyclerview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.myrecyclerview.databinding.ActivityDetailHeroBinding
import java.util.concurrent.Executors

class DetailActivityHero : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHeroBinding
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Hero"
        val detailHero = intent.getParcelableExtra<Hero>("DATA") as Hero
        val executor = Executors.newSingleThreadExecutor()      //executor to parse the url
        val handler = Handler(Looper.getMainLooper())           //handler untuk load gambar ke UI

        //inisialiasi image
        var image: Bitmap? = null

        val text = """
            Nama        : ${detailHero.name}
            Deskripsi   : ${detailHero.description} 
        """.trimIndent()

        //menampilkan gambar dari URL
        executor.execute {
            val img = detailHero.photo

            //mengambi gambar dan mem-post di fieldImg dengan bantuan handler yang sudah di buat
            try {
                val `in` = java.net.URL(img).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post{
                    binding.detailImg.setImageBitmap(image)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }

        }

        //menampilkan gambar dari lokal
//        fieldImg.setImageResource(img)
        binding.fieldDetailHero.text = text
    }
}