package com.dicoding.myintentapp

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //variabel text view result untuk menyimpan nilai dari activity
    private lateinit var tvResult: TextView

    //agar activity dapat mengembalikan sebuah nilai
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            tvResult.text = "Hasil : $selectedValue"
        }
    }

    /*
    * Anda perlu mendaftarkan jenis kembalian ke sistem dengan menggunakan kode registerForActivityResult dengan parameter
    * ActivityResultContract berupa ActivityResultContract. Hal ini karena kita akan mendapatkan nilai kembalian
    * setelah memanggil Activity baru.*/

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

        /* Logika Tombol Dial Number*/
        val btnDial: Button = findViewById(R.id.btn_dialUp)
        btnDial.setOnClickListener(this)

        /* Logika Tombol moveForResult*/
        val btnMoveForResult: Button = findViewById(R.id.btn_moveForResult)
        btnMoveForResult.setOnClickListener(this)

        //identifikasi textview hasil result
        tvResult = findViewById(R.id.tv_result)

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

            //jika menekan tombol dial up
            R.id.btn_dialUp -> {
                //nomor telepon yang akan dipakai
                val phoneNum = "082215760138"

                //intent secara implisit dengan parameter ACTION dan URI
                val dialUpIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNum"))
                /*
                * ACTION_DIAL merupakan parameter untuk menentukan ntent filter dari aplikasi-aplikasi yang bisa menangani
                * action tersebut.
                *
                * Uri adalah sebuah untaian karakter yang digunakan untuk mengidentifikasi nama, sumber, atau layanan di
                * internet sesuai dengan RFC 2396. Pada Uri.parse("tel:"+phoneNumber), kita melakukan parsing uri dari bentuk teks
                * string menjadi sebuah obyek uri dengan menggunakan metode static parse(String).
                *
                * Dimana tel adalah sebuah skema untuk sumber daya telepon dan phoneNumber adalah nomor telepon
                * yang sudah ditentukan sebelumnya, URI dapat digunakan untuk peta (geo) dan browser (http)*/

                startActivity(dialUpIntent)
            }

            //pindah activity ke moveForResult
            R.id.btn_moveForResult -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)

                //ketika ingin membuat sebuah variabel mengembalikan nilai maka kode yang dipakai adalah method launch
                resultLauncher.launch(moveForResultIntent)

            }
        }
    }
}