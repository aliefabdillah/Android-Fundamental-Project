package com.dicoding.mybroadcastreceiver

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class DownloadService : JobIntentService(){
    companion object {
        val TAG: String = DownloadService::class.java.simpleName

        //alternatif supaya tidak loop
        private const val JOB_ID = 1000
//        fun enqueueWork(context: Context, intent: Intent){
//            enqueueWork(context, DownloadService::class.java, JOB_ID, intent)
//        }
    }

    //kalo pake fungsi ini bakal loop terus menerus saat mendownload
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            enqueueWork(this, this::class.java, 101, intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    //method ini digunakan unutk melakukan unduh file secara asynchronus di background
    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "Download Service Dijalankan")
        try {
            //melakukan proses sleep 5 detik
            Thread.sleep(5000)
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        //lalu membroadcast sebuah intentFilter dengan nilai kembalian variabel ACTION_DOWNLOAD_STATUS
        val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
        sendBroadcast(notifyFinishIntent)
    }



}