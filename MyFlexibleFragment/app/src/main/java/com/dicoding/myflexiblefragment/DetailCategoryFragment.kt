package com.dicoding.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class DetailCategoryFragment : Fragment(){
    lateinit var tvCategoryName: TextView
    lateinit var tvCategoryDescription: TextView
    lateinit var btnProfile: Button
    lateinit var btnShowDialog: Button

    var description: String? = null

    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCategoryName = view.findViewById(R.id.tv_category_name)
        tvCategoryDescription = view.findViewById(R.id.tv_category_description)
        btnProfile = view.findViewById(R.id.btn_profile)
        btnShowDialog = view.findViewById(R.id.btn_show_dialog)

        //tombol menampilkan dialog form
        btnShowDialog.setOnClickListener{
            val mOptionDialogFragment = OptionDialogFragment()

            //pemanggilan fragment dialog form
            /*
            * perbedaannya disini menggunakan childFragmentManager, yang merupakan sebuah nested fragment.
            * Karena OptionDialogFragment dipanggil di dalam sebuah obyek fragment yang telah ada sebelumnya
            * yaitu DetailCategoryFragment*/
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
        }

        //tombol untuk beralih ke activity profile
        btnProfile.setOnClickListener {
            //caranya mirip seperti berpindah activity biasa
            /*
            * perbedaannya pada fragment harus menggunakan requireActivity untuk parameter
            * context nya karena fungsi this hanya bisa dipanggil melalui activity,
            * sedangkan untuk class nya masih sama*/
            val mIntentProfile = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntentProfile)
        }

        if (savedInstanceState != null){
            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = descFromBundle
        }

        if (arguments != null){
            //mengambil data yang telah dipindah dari properti arguments
            val categoryName = arguments?.getString(EXTRA_NAME)
            tvCategoryName.text = categoryName

            //mengambil data dari properti description
            tvCategoryDescription.text = description
        }
    }

    //pengimplmentasian interface untuk event setelah menekan tombol adalah memunculkan sebuah Toast
    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object : OptionDialogFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }

}