package com.dicoding.myintentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Logika Tombol moveActivity */
        //menidentifikasi view berdasarkan id dari button moveActivity
        val btnMoveActivity: Button = findViewById(R.id.btn_moveActivity)
        //fungsi ketika tombol moveActivity ditekan
        btnMoveActivity.setOnClickListener(this)

        /* Logika Tombol moveWithData*/
        val btnMoveWithData: Button = findViewById(R.id.btn_moveWithData)
        btnMoveWithData.setOnClickListener(this)

        /* Logika Tombol moveWithObject*/
        val btnMoveWithObject: Button = findViewById(R.id.btn_moveWithObject)
        btnMoveWithObject.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        //melakukan logika percabangan tombol
        when(view?.id){
            //jika parameter ada tombol moveActivity
            R.id.btn_moveActivity -> {
                //kode berpindah activity tanpa membawa data menggunakan intent
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                /*
                * parameter pertama pada class intent digunakan untuk identifikasi activity yang sedang aktif
                * saat ini (Context) yaitu kelas MainActivity, dan parameter kedua dipakai untuk tujuan activity
                * yang akan dituju yaitu berupa kelas MoveAcitvity*/

                //berpindah activity tanpa membawa data dengan objek dari moveIntent
                startActivity(moveIntent)
            }

            //jika parameter tombol moveWithData
            R.id.btn_moveWithData -> {
                //berpindah activity dengan membawa data dari main activity ke moveWithDataActivity
                val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)

                //menambahkan extra nilai ke dalam intent berupa nama dan umur
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Alief Muhammad Abdillah")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 19)
                /*
                * Pda method putExtra terdapat 2 parameter yaitu nama variabel extra pada activity moveWithData
                * untuk menampung nilai yang akan di pindahkan dan nilai yang akan di pindahkan antar activiy
                * itu sendiri*/

                //start activity baru dengan objek moveWithDataIntent
                startActivity(moveWithDataIntent)
            }

            //parameter masukan fungsi adalah tombol moveWithObject
            R.id.btn_moveWithObject -> {
                //inisiilasi objek dari data class person
                val person = Person(
                    "Alief MA",
                    19,
                    "aliefmabdillah09@gmail.com",
                    "Bandung"
                )

                //memindahkan data objek dari MainActivity ke MovewithObject Activity
                val moveWithObjectIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)

                /* Menggunakan putextra mirip seperti memindahkan data, hanya saja parameter nilainya diganti
                * dengan objek Parcelable yang akan di pindahkan
                *
                * Untuk memindahkan arraylist data dapat menggunakan method putParcelableArrayListExtra.*/
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)

                startActivity(moveWithObjectIntent)
            }
        }
    }
}