package com.dicoding.mytablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/* Class untuk mengatur viewPager2 dan Tab Layout yang mengextend abstract class FragmentStateAdapter
* sebenarnya kita dapat menggunakan juga recyclerView.adapter karena pager juga dibuat dengan recycler view*/
class SectionPagerAdapater(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    /*
    * Fungsi ini digunakan untuk menampilkan fragment sesuai posisi tab nya*/
    override fun createFragment(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = HomeFragment()          //tab 0/pertama menampilkan Home Fragment
//            1 -> fragment = ProfileFragment()       //tab 1/kedua menampilkan Profile Fragment
//        }
//        return fragment as Fragment

        //create tabLayout using one fragment
        val fragment = HomeFragment()

        /*
        * tab Layout dengan hanya menggunakan satu fragment,
        * jika isi fragment mirip dapat diakali dengan mengirim data menggunakan bundle*/
        fragment.arguments = Bundle().apply {
            //menyimpan data fragment dan posisi fragment yang dimulai dari 1 ke sebuah fragment.arguments
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

    /*
    * Jumlah tab yang akan ditampilkan*/
    override fun getItemCount(): Int {
//        return 2

        //Mengubah jumlah fragment menjadi 3
        return 3
    }

}