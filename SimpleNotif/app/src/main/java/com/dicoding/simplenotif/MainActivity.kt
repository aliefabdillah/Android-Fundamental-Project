package com.dicoding.simplenotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding_channel"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //aksi onclick pada layout button
    /*
    *Kelebihan dari cara ini yakni kita tidak perlu menginisialisasi Button tersebut di onCreate,
    * sehingga pemanggilan kode bisa lebih cepat dengan syarat memiliki paramter view pada method. */
    fun sendNotification(view: View){
        //menambahkan pending Intent ke sebuah URL, selain ke sebuah URL dapat juga menambahkan intent untuk memanggil activity
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                PendingIntent.FLAG_IMMUTABLE
            }else{
                0
            }
        )

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //menambahkan berbagai macam elemen pada notifikasi
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)                                //intent ke sebuah activity, url, atau activity lain
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)          //ikon tang muncul pada status bar (wajib)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_notifications_24)) // Ikon ini yang akan muncul di sebelah kiri dari text notifikasi.
            .setContentText(resources.getString(R.string.content_text))     // Judul dari notifikasi (wajib ada).
            .setContentTitle(resources.getString(R.string.content_title))   // Text yang akan muncul di bawah judul notifikasi (wajib ada).
            .setSubText(resources.getString(R.string.subtext))              //Text ini yang akan muncul di bawah content text.
            .setAutoCancel(true)                                            // Digunakan untuk menghapus notifikasi setelah ditekan.

        //untuk android oreo ke atas harus menambahkan notification channel dibawah
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //create or update
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        //kode dibawah digunakan untuk membuat notification ke system dan menampilkan sesuai dengan id yang diberikan
        //fungsi ini juga dapat digunakan untuk membatalkan notifikasi yang sudah muncul
        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }
}