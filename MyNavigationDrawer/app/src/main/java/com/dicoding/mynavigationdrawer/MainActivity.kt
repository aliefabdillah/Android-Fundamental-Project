package com.dicoding.mynavigationdrawer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mynavigationdrawer.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var profileCircleImageView: CircleImageView
    private var profileImageUrl = "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rY-jrtntAi4/s640-il/photo.jpg"

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /*
        * snakbar digunakan untuk mengatur listerner ketika FAB dijalankan
        * snackbar merupakan suksesor dari toast
        * Perbedaan mendasar adalah pada Snackbar Anda bisa menambahkan sebuah action untuk
        * melakukan sebuah aksi tertentu. Hal ini tidak bisa dilakukan pada toast.*/
        binding.appBarMain.fab.setOnClickListener { view ->
            //tanpa action
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            //dengan action
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action") {Toast.makeText(this@MainActivity, "Halo ini action dari snackbar", Toast.LENGTH_SHORT)
                    .show()}
                .show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        /*
        * untuk mengubah komponen view yang terdapat pada header sebuah navigation view maka
        * perlu proses casting/inisialisasi komponen dengan kode berikut */
        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.imageView)

        //load gambar ke imageview
        Glide.with(this@MainActivity)
            .load(profileImageUrl)
            .circleCrop()
            .into(profileCircleImageView)

        /*
        * kode untuk otomatisasi perpindahan antar fragment*/
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_cart
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /*
        * AppBarConfiguration berisi kumpulan id yang ada di dalam menu NavigationDrawer (baris 3). Jika id yang ada di dalam menu NavigationDrawer ditambahkan di AppBarConfiguration, maka AppBar akan berbentuk Menu NavigationDrawer. Jika tidak ditambahkan, maka akan menampilkan tanda panah kembali.
        * setupActionBarWithNavController digunakan untuk mengatur judul AppBar agar sesuai dengan Fragment yang ditampilkan.
        * Dan yang terakhir, setupWithNavController digunakan supaya NavigationDrawer menampilkan Fragment yang sesuai ketika menu dipilih.*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*
    * method ini digunakan ketika tombol back ditekan maka aplikasi tidak akan langsung keluar ,
    * malinkan akan menuju ke startDestination yang ada pada navigation graph yaitu homegragment*/
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}