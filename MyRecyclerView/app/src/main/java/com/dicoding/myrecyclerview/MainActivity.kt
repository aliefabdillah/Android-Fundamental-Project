package com.dicoding.myrecyclerview

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvHeroes: RecyclerView
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHeroes = findViewById(R.id.rv_heroes)
        //mengatur fixed size recycler view berupa lebar dan tinggi secara otomatis.
        //nilai lebar dan tinggi dari RecylerView akan menjadi Konstan
        rvHeroes.setHasFixedSize(true)

        //menampilkan data ke dalam activity
        list.addAll(listHeroes)
        showRecyclerList()
    }

    private val listHeroes: ArrayList<Hero>
        //memanggil data yang sudah dibuat di resource string.xml
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listHero = ArrayList<Hero>()
            for (i in dataName.indices){
                val hero = Hero(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
                listHero.add(hero)
            }
            return listHero
        }

    //mengatur layout dan menampilkan recyclerView
    private fun showRecyclerList(){
        //mengatur layout berdasarkan orientasi layar
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rvHeroes.layoutManager = GridLayoutManager(this, 2)          //orientasi layout secara grid dengan 2 column
        }else {
            rvHeroes.layoutManager = LinearLayoutManager(this)      //orientasi layout secara linear
        }
        val listHeroAdapter = ListHeroAdapter(list)
        rvHeroes.adapter = listHeroAdapter

        //mengimplementasikan pada recyclerView fungsi click
        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }

    //membuat toast ketika mengclikc pilihan hero
    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }
}