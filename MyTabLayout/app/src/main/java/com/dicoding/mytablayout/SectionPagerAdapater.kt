package com.dicoding.mytablayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/* Class untuk mengatur viewPager2 dan Tab Layout yang mengextend abstract class FragmentStateAdapter
* sebenarnya kita dapat menggunakan juga recyclerView.adapter karena pager juga dibuat dengan recycler view*/
class SectionPagerAdapater(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    /*
    * Fungsi ini digunakan untuk menampilkan fragment sesuai posisi tab nya*/
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()          //tab 0/pertama menampilkan Home Fragment
            1 -> fragment = ProfileFragment()       //tab 1/kedua menampilkan Profile Fragment
        }
        return fragment as Fragment
    }

    /*
    * Jumlah tab yang akan ditampilkan*/
    override fun getItemCount(): Int {
        return 2
    }

}