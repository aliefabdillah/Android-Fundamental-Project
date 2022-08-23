package com.dicoding.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //inisialisasi variabel untuk menampung data masukan
    private lateinit var edtWidht: EditText
    private lateinit var edtLength: EditText
    private lateinit var edtHeight: EditText
    private lateinit var resultView: TextView
    private lateinit var btnCalculate: Button

    //save instance
    companion object {
        //constanta untuk state result
        private const val STATE_RESULT = "state_result"
    }

    //fungsi bawaan save instance state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, resultView.text.toString())    //menyimpan nilai result view ke bundle di state result
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
        setContentView(R.layout.activity_main)

        edtWidht = findViewById(R.id.edit_width)    //mengambil id dari input lebar
        edtHeight = findViewById(R.id.edit_height)  //mengambil id dari input tinggi
        edtLength = findViewById(R.id.edit_length)  //mengambil id dari input panjang
        btnCalculate = findViewById(R.id.btn_calculate) //mengambil id dari tombol result
        resultView = findViewById(R.id.result_view)     //mengambil id dari text view hasil

        btnCalculate.setOnClickListener(this)           //menambahkan fungsi setOnClick pada button

        //jika variabel save instance tidak kosong
        if (savedInstanceState != null){
            //mengambil value result perhitungan dari key state result
            val result = savedInstanceState.getString(STATE_RESULT)
            //dan menampilkan kembali di text view hasil
            resultView.text = result
        }
    }

    override fun onClick(v: View?) {
        //jika parameter adalah button id
        if (v?.id == R.id.btn_calculate){
            //mengambil data string dari input
            val inputL = edtLength.text.toString().trim()
            val inputW = edtWidht.text.toString().trim()
            val inputH = edtHeight.text.toString().trim()

            //penanda apakah field input kosong atau tidak
            var isEmptyFields = false

            //jika field input panjang kosong
            if (inputL.isEmpty()){
                isEmptyFields = true
                edtLength.error = "Field ini tidak boleh kosong"
            }
            //jika field input lebar kosong
            if (inputW.isEmpty()){
                isEmptyFields = true
                edtWidht.error = "Field ini tidak boleh kosong"
            }
            //jika field input tinggi kosong
            if (inputH.isEmpty()){
                isEmptyFields = true
                edtHeight.error = "Field ini tidak boleh kosong"
            }

            //jika tida ada field yang kosong
            if (!isEmptyFields)
            {
                //algoritma untuk menghitung volume
                val volume = inputL.toDouble() * inputW.toDouble() * inputH.toDouble()
                resultView.text = volume.toString()     //mengubah nilai result menjadi nilai vulume yang dihasilkan
            }
        }
    }
}