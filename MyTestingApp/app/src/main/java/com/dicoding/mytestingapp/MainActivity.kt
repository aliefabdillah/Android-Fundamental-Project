package com.dicoding.mytestingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnSetValue: Button
    private lateinit var tvText: TextView

    private var name = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText = findViewById(R.id.tv_text)
        btnSetValue = findViewById(R.id.btnValue)       //tidak akan error karena button sudah di inisilasasi

        btnSetValue.setOnClickListener(this)

        name.add("Kevin")
        name.add("Yoga")
        name.add("Rendi")
        /*
        btnSetValue!!.setOnClickListener(this)

        kode fungsi clikc button di atas akan error karena kita mencoba menekan button yang belum
        diinisiasi (masih bernilai null).
        */
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnValue) {
            Log.d("MainActivity", name.toString())
            val names = StringBuilder()

            /*ketika for nya melebihi index yang ada maka akan muncur error indexoutofbounds*/
            for (i in 0..2) {
                names.append(name[i]).append("\n")
            }

            tvText.text = names.toString()
        }
    }
}