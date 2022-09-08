package com.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mencoba membuat proses di UI Thread dengan simulasi proses Compress yang memakan waktu 5 detik

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        /*
        * Dengan kode dibawah maka akan terjadi lagging pada UI, apalagi jika sleep diubah menjadi 500000 Millis
        * maka aplikasi akan terjadi Aplication Not Responding, agar hal tersebut tidak terjadi maka
        * perlu menggunakan kode Executor untuk membuat thread baru di belakang main thread saat ini
        * dan handler untuk update hasil dari proses ke komponen UI*/

        val executor = Executors.newSingleThreadExecutor()      //executor untuk mengataasi background proses
        val handler = Handler(Looper.getMainLooper())           //variabel handler

        btnStart.setOnClickListener {
            //kode executor untuk menjalan kan kode pada thread baru
            executor.execute {
                try {
                    //simulate process compressing
                    for (i in 0..10){
                        Thread.sleep(500)
                        val percentage = i*10

                        //handler untuk update ui di main thread
                        handler.post {
                            if (percentage == 100){
                                tvStatus.setText(R.string.task_completed)
                            }else{
                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
                            }
                        }
                    }
                }catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}