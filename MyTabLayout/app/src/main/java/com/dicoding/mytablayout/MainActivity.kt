package com.dicoding.mytablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        * Digunakan untuk men-set PagerAdapter ke viewPager*/
        val sectionPagerAdapter = SectionPagerAdapater(this@MainActivity)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        /*
        * Menghubungkan ViewPager dengan tabLayout dan akan sesuai dengan posisi yang dipilih pada tab*/
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])        //menentukan judul masing" tab berdasarkan posisinya
        }.attach()

        supportActionBar?.elevation = 0f
    }


}