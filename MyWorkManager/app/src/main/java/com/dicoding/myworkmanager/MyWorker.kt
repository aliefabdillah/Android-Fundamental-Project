package com.dicoding.myworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import java.text.DecimalFormat
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        private val TAG = MyWorker::class.java.simpleName
        const val APP_ID = "bd2bb7d0101d16b32e9581a9997b8b24"       //api key
        const val EXTRA_CITY = "city"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding_channel"
    }

    private var resultStatus: Result? = null

    /*
    * Method ini dipanggila ketika WorkManager berjalan dan kode didalamnya akan dijalankan
    * pada background thread secara otomatis. method ini mengembalikan nilai berupa result yang berfungsi
    * untuk mengetahui status work manager yang berjalan
    *   Result.success(), result yang menandakan berhasil.
        Result.failure(), result yang menandakan gagal.
        Result.retry(), result yang menandakan untuk mengulang task lagi.*/
    override fun doWork(): Result {
        //mendapatkan data yang dikirimkan dari mainactivity
        val dataCity = inputData.getString(EXTRA_CITY)
        return getCurrentWeather(dataCity)
    }

    private fun getCurrentWeather(city: String?): Result {
        Log.d(TAG, "getCurrentWeather: Mulai....")
        Looper.prepare()

        //menggunakanan SyncHttpClient untuk koneksi yang bersifat Synchronus
        /*
        * Khusus di WorkManager Anda perlu menjalankan proses secara synchronous supaya bisa
        * mendapatkan result success. Selain itu WorkManager sudah berjalan di background thread,
        * jadi aman saja menjalankan secara langsung. Namun jika Anda ingin menggunakan LoopJ di
        * Activity, maka gunakanlah AsyncHttpClient supaya tidak terjadi error NetworkOnMainThread.*/
        val client = SyncHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$APP_ID"
        Log.d(TAG, "getCurrentWeather : $url")
        client.post(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val currentWeather: String = responseObject.getJSONArray("weather").getJSONObject(0).getString("main")
                    val description : String = responseObject.getJSONArray("weather").getJSONObject(0).getString("description")
                    val tempKelvin = responseObject.getJSONObject("main").getDouble("temp")
                    val tempCelcius = tempKelvin - 273
                    val temperature: String = DecimalFormat("##.##").format(tempCelcius)
                    val title = "Current Weather in $city"
                    val message = "$currentWeather, $description with $temperature celcius"
                    showNotification(title, message)
                    
                    Log.d(TAG, "onSuccess: Selesai...")
                    resultStatus = Result.success()             //kembalian result menandakan proses berhasil
                }catch (e: Exception){
                    showNotification("Get Current Weather Not Success", e.message)
                    Log.d(TAG, "onSuccess: Gagal..")
                    resultStatus = Result.failure()             //nilai kembalian result menandakan proses gagal
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d(TAG, "onFailur: Gagal..")
                showNotification("Get Current Weather Not Success", error.message)
                resultStatus = Result.failure()
            }
        })

        return resultStatus as Result
    }

    private fun showNotification(title: String, message: String?) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())

    }

}