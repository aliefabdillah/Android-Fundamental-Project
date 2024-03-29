package com.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        val executor = Executors.newSingleThreadExecutor()      //membuat hanya satu thread lain untuk menjalankan proses
        val handler = Handler(Looper.getMainLooper())           //variabel handler untuk menjalankan proses di dalam handler pada main thread

        //Kode dengan Executor dan handler
//        btnStart.setOnClickListener {
//            //kode executor untuk menjalan kan kode pada thread baru
//            executor.execute {
//                try {
//                    //simulate process compressing
//                    for (i in 0..10){
//                        Thread.sleep(500)
//                        val percentage = i*10
//
//                        //handler untuk update ui di main thread
//                        handler.post {
//                            if (percentage == 100){
//                                tvStatus.setText(R.string.task_completed)
//                            }else{
//                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                            }
//                        }
//                    }
//                }catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }

        //kode dengan coroutines
        btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default){
                //simulate process in background thread
                for (i in 0..10){
                    delay(500)
                    val percentage = i * 10

                    //berpindah thread ke main thread
                    withContext(Dispatchers.Main){
                        //update ui in main thread
                        if (percentage == 100){
                            tvStatus.setText(R.string.task_completed)
                        }else{
                            tvStatus.text = String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }
    }
}