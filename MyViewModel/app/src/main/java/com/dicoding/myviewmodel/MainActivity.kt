package com.dicoding.myviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    /*Deklrasi variabel untuk inisialiasi viewModel*/
//    private lateinit var viewModel: MainViewModel
    /*Deklarasi dan inisialiasi class view model dengan lebih sederhana*/
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Inisialisasi class viewModel
        *
        * ViewModelProvider diguanakan untuk menghubungkan activity dengan class View Model
        * cukup memanggil kelas ViewModelProvider dengan parameter context. Karena inisialisasi
        * dilakukan di Activity, maka kita menggunakan this sebagai context. Kemudian input
        * .get() diisi dengan kelas ViewModel mana yang akan dihubungkan dengan Activity. */

//        viewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)

        //method menyimpan nilai perubahan result ke tvResult dan menampilkan di UI (masih secara manual)
        displayResult()

        //listener button hitung
        binding.btnCalculate.setOnClickListener {
            val width = binding.edtWidth.text.toString()
            val length = binding.edtLength.text.toString()
            val height = binding.edtHeight.text.toString()

            when {
                width.isEmpty() -> {
                    binding.edtWidth.error = "Tidak Boleh Kosong"
                }
                length.isEmpty() -> {
                    binding.edtLength.error = "Tidak Boleh Kosong"
                }
                height.isEmpty() -> {
                    binding.edtHeight.error = "Tidak Boleh Kosong"
                }
                else -> {
                    viewModel.calculate(width, length, height)
                    //method menyimpan nilai perubahan result ke tvResult dan menampilkan di UI (masih secara manual)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() {
        //mendapatkan result dari viewModel
        binding.tvResult.text = viewModel.result.toString()
    }
}