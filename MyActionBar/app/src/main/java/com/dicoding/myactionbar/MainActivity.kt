package com.dicoding.myactionbar

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.dicoding.myactionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /*
    * Fungsi ini digunakan untuk menampilkan custom item pada action bar*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        //identifikasi listenen untuk searchview
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView            //mengambil komponen search view yang telah di inflate

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)                //memberikan hint pada pengguna tentang query search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            * Method ketika search selesai atau ketika tombol submit ditekan*/
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()             //clear focus digunakan agar tidak ada duplikasi dalam pemanggilan fungsi
                return true
            }

            /*
            * method untuk merespon tiap perubahan huruf pada search view
            * */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    /*
    * Fungsi untuk menangani action yang terjadi pada action bar ketika di klik*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MenuFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return false
        }
    }

    /*
    * Setelah menampilkan item menggunakan metode onCreateOptionsMenu(), kita tinggal memasang
    * event listener untuk dijalankan ketika item tersebut dipilih. Listener click pada menu
    * action bar dapat memanfaatkan onOptionsItemSelected().*/
}