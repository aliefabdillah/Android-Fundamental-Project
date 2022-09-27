package com.dicoding.myalarmmanager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"

        //inten key
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        //2 id untuk 2 macam alarm
        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101

        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"
    }

    /*
    * Method OnReceive dijalankan ketika waktu yang ditentukan user sama dengan waktu system*/
    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val title = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) TYPE_ONE_TIME else TYPE_REPEATING
        val notifId = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONETIME else ID_REPEATING

        if (message != null){
            showAlarmNotification(context, title, message, notifId)
        }
        showToast(context, title, message)
    }

    private fun showToast(context: Context, title: String, message: String?){
        Toast.makeText(context, "$title: $message", Toast.LENGTH_SHORT).show()
    }

    fun setOneTimeAlarm(context: Context, type: String, date: String, time: String, message: String){

        if (isDateInvalid(date, DATE_FORMAT) || isDateInvalid(time, TIME_FORMAT)) return

        //membuat sebuah object AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //inten yang menjalankan AlarmReceiver yang mengirimkan message dan type alarm
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        Log.e("ONE TIME", "$date $time")
        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        //membuat object calendar
        val calendar = Calendar.getInstance()
        //memecah data calendar menjadi tahun, bulan, tanggal, jam, menit
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)                //pada array bulan dikurangi 1 karena indeks month dimulai dari 0
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        //penggunaan pending intent karena waktu mengirim inten alarm akan sesama ketika waktu sistem
        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONETIME, intent, PendingIntent.FLAG_IMMUTABLE)       //perbedaan satu alarm dengan alarm lain adalah ID nya

        /*
        * kode ini maksudnya adalah kita memasang alarm yang dibuat dengan tipe RTC_WAKEUP. Tipe
        * alarm ini dapat membangunkan peranti (jika dalam posisi sleep) untuk menjalankan
        * obyek PendingIntent.  */
        /*
        * Ketika kondisi sesuai, maka akan BroadcastReceiver akan running dengan semua proses yang terdapat di dalam metode onReceive().*/
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(context, "One Time Alarm Set Up", Toast.LENGTH_SHORT).show()
    }

    //cek apakah format date invalid atau tidak
    private fun isDateInvalid(date: String, format: String): Boolean{
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        }catch (e: ParseException){
            true
        }
    }

    //fungsi membuat dan menampilkan notification
    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int){
        val channelId = "Channel_1"
        val channelName = "AlarmManager Channel"

        //membuat notifcation
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_access_time_24)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        //jika versi android adalah oreo ke atas maka harus membuat channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        //menampilkan notifikasi
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }
}