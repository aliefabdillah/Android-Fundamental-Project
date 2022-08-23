package com.dicoding.myintentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MoveWithObjectActivity : AppCompatActivity() {
    companion object {
        //variabel static untuk menyimpan object dari activity lain
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_object)

        //identifikasi text view di activity
        val tvObject: TextView = findViewById(R.id.tv_object_received)

        //mengambil objek dari variabel EXTRA PERSON
        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person

        //menampilkan di textview
        val text = """
            Name    : ${person.name.toString()}
            Email   : ${person.email.toString()}
            Age     : ${person.age.toString()}
            City    : ${person.city.toString()}
        """.trimIndent()
        tvObject.text = text

    }
}