package com.dicoding.myflexiblefragment

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class HomeFragment : Fragment(), View.OnClickListener {

    /*
    * metode onCreateView() berfungsi unuk layout interface didefinisikan dan ditransformasikan dari layout
    * berupa file xml ke dalam objek view dengan menggunakan metode inflate().*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
        /*
        * fungsi inflate merupakan objek dari LayoutInflater yang berfungsi untuk mengubah layout xml ke dalam bentuk objek viewgroup
        * atau widget. fungsi inflate di sini yaitu untuk menampilkan layout dari Fragment, di mana layout yang ditampilkan
        * di sini yaitu fragment_home.*/
    }

    /*
    * Methode ini digunakan untuk menginisialiasi view dan mengatur action dari view*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCategory: Button = view.findViewById(R.id.btn_category)      //pemanggilan findviewbyid harus menggunakan variabel view
        /*
        * Kode view.findViewById tersebut menandakan btn_category berada pada objek view di mana objek view
        * berasal dari konversi fragment_home.xml. Bila hanya findViewById(R.id.btn_category), maka btn_category
        * berada pada root layout, activity_main.xml.*/

        btnCategory.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        //action untuk berpindah fragment dari home fragment ke category fragment
        if (v.id == R.id.btn_category) {
            //instansiasi objek category fragment
            val mCategoryFragment = CategoryFragment()

            /*
            pada kode ini menggunakan parentFragmentManager yang berguna untuk mendapatkan Fragment
            Manager dari Activity
            */
            val mFragmentManager = parentFragmentManager
            /*
            Tanpa KTX
            mFragmentManager.beginTransaction().apply {
                *//*ketika inin menempelkan sebuah fragment baru yaitu menggunakan methde replace() bukan add()
                * Replace ini akan mengganti objek lama dan menambahkan objek baru ke dalam layout
                *
                * parameter eprtama yaitu objek fragment saat ini, parameter kedua yaitu objek fragment
                * yang baru yang akan dimasukan*//*
                replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)

                *//*
                * Kita menggunakan fungsi addToBackStack karena objek fragment yang saat ini diciptakan
                * masuk ke dalam sebuah fragment stack.  Nantinya ketika kita tekan tombol back,
                * ia akan pop-out keluar dari stack dan menampilkan objek fragment sebelumnya, yaitu HomeFragment.*//*
                addToBackStack(null)
                commit()
            }*/

            /*dengan KTX*/
            mFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)
            }
        }
    }
}