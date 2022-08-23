package com.dicoding.myintentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MoveWithDataActivity : AppCompatActivity() {

    //variable constant untuk menampung data haris intent
    companion object {
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_NAME = "extra name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_data)

        //mendidentifikasi text view di moveWithData Activity
        val tvDataReceived: TextView = findViewById(R.id.tv_data_received)

        //menyimpan data hasil intent di variabel baru
        val name = intent.getStringExtra(EXTRA_NAME)                //mengubah data intent menjadi string
        val age = intent.getIntExtra(EXTRA_AGE, 0)      //mengubah data intent menjadi int

        //menampilkan data di text view
        val text = "Name : $name, Age : $age"
        tvDataReceived.text = text
    }
}