package com.dicoding.myflexiblefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class CategoryFragment : Fragment(), View.OnClickListener {

    //method ini berfungsi untuk mengubah layout xml ke bentuk objek view memakai inflate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    /*
    * Methode ini digunakan untuk menginisialiasi view dan mengatur action dari view*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDetailCategory: Button = view.findViewById(R.id.btn_detail_category)
        btnDetailCategory.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_detail_category){
            //memindahkan data antar fragment

            //instansisasi objek DetailCategoryFragment
            val mDetailCategoryFragment = DetailCategoryFragment()

            //memindahkan data dengan bundle
            val mBundle = Bundle()
            mBundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle")
            /*
            * Pada kode di atas kita menggunakan obyek bundle untuk mengirimkan data antar fragment.
            * caranya mirip seperti memindahkan data antar activity (intent)*/

            //memindakan data dengan setter dan getter
            val description = "Kategori ini akan berisi produk-produk lifestyle"

            //memindahkan dari objek bundle ke default properti argument yang dimiliki oleh class
            mDetailCategoryFragment.arguments = mBundle
            //memindahkan variable ke properti pada class
            mDetailCategoryFragment.description = description

            //proses berpindah fragment
            val mFragmentManager = parentFragmentManager
            /*
            Tanpa KTX
            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mDetailCategoryFragment, DetailCategoryFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }*/

            //dengan KTX
            mFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, mDetailCategoryFragment, DetailCategoryFragment::class.java.simpleName)
            }
        }
    }
}