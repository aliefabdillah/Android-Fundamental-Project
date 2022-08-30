package com.dicoding.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.dicoding.barvolume.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //inisialisasi variabel untuk menampung data masukan

    //save instance
    companion object {
        //constanta untuk state result
        private const val STATE_RESULT = "state_result"
    }

    //variable untuk view binding
    private lateinit var binding: ActivityMainBinding

    //fungsi bawaan save instance state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, binding.resultView.text.toString())    //menyimpan nilai result view ke bundle di state result
        /*
        * menyimpan kode ini menggunakan konsep key-value, dengan STATE_RESULT merupakai key dan valuenya adalah resultView
        * sebagai string
        *
        * Fungsi onSaveInstanceState akan dipanggil secara otomatis sebelum sebuah Activity hancur.
        * Di sini kita perlu menambahkan onSaveInstanceState karena ketika orientasi berubah,
        * Activity tersebut akan di-destroy dan memanggil fungsi onCreate lagi, sehingga kita perlu menyimpan
        * nilai hasil perhitungan tersebut supaya data tetap terjaga dan tidak hilang ketika orientasi berubah.*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)       //inisilalisasi binding view
        setContentView(binding.root)                                //set content binding ke root

        //maka untuk mendapatkan view id tinggal menambahkan kode binding di setiap element view
        binding.btnCalculate.setOnClickListener(this)           //menambahkan fungsi setOnClick pada button

        //jika variabel save instance tidak kosong
        if (savedInstanceState != null){
            //mengambil value result perhitungan dari key state result
            val result = savedInstanceState.getString(STATE_RESULT)
            //dan menampilkan kembali di text view hasil
            binding.resultView.text = result
        }
    }

    override fun onClick(v: View?) {
        //jika parameter adalah button id
        if (v?.id == R.id.btn_calculate){
            //mengambil data string dari input
            val inputL = binding.editLength.text.toString().trim()
            val inputW = binding.editWidth.text.toString().trim()
            val inputH = binding.editHeight.text.toString().trim()

            //penanda apakah field input kosong atau tidak
            var isEmptyFields = false

            //jika field input panjang kosong
            if (inputL.isEmpty()){
                isEmptyFields = true
                binding.editLength.error = "Field ini tidak boleh kosong"
            }
            //jika field input lebar kosong
            if (inputW.isEmpty()){
                isEmptyFields = true
                binding.editWidth.error = "Field ini tidak boleh kosong"
            }
            //jika field input tinggi kosong
            if (inputH.isEmpty()){
                isEmptyFields = true
                binding.editHeight.error = "Field ini tidak boleh kosong"
            }


            //jika tida ada field yang kosong
            if (!isEmptyFields)
            {
                //algoritma untuk menghitung volume
                val volume = inputL.toDouble() * inputW.toDouble() * inputH.toDouble()
                binding.resultView.text = volume.toString()     //mengubah nilai result menjadi nilai vulume yang dihasilkan
            }
        }
    }
}