package com.dicoding.myflexiblefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //penggunaan instance dari fragment manager yang merupakan UI untuk mengorganisir objek fragment pada sebuah acitivty
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = HomeFragment()      //instansiasi objek homefragment
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
            /*
            Tanpa menggunakan KTX
            mFragmentManager
                //untuk memulai proses perubahan
                .beginTransaction()

                //menembahkan objek fragment ke dalam layout container
                *//*
                *ayout container ini merupakan objek framelayout dengan ID frame_container. Ia memiliki
                * tag dengan nama kelas dari HomeFragment itu sendiri. *//*
                .add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)

                // untuk mengeksekusi pemasangan fragment untuk ditampilkan ke antarmuka pengguna
                .commit()*/

            /* Dengan KTX */
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
            }
        }
    }


}