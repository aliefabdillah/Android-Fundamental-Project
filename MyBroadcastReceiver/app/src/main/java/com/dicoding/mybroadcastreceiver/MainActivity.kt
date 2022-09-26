package com.dicoding.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dicoding.mybroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var downloadReceiver: BroadcastReceiver
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)
        binding?.btnDownload?.setOnClickListener(this)

        //membuat sebuah object DownloadReceiver
        downloadReceiver = object : BroadcastReceiver(){
            //method ini otomatis di jalankan ketika DownloadReceiver Mengirim Intent
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(DownloadService.TAG, "Download Selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }

        //mengambild data intentFilter dari DownloadReceiver
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        //meregistrasikan downloadReceiver dengan event downloadIntenFilter
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    //variabel for permission check
//    var requstPermissionLaucher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            Toast.makeText(this, "Sms Receiver permission diterima", Toast.LENGTH_SHORT).show()
//        }else{
//            Toast.makeText(this, "Sms Receiver Permission ditolak", Toast.LENGTH_SHORT).show()
//        }
//
//    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_permission -> PermissionManager.check(this, android.Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE)
            R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
//                DownloadService.enqueueWork(this, downloadServiceIntent)
                startService(downloadServiceIntent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //ketika activity dimatikan maka harus mencopot downloadReceiver yan telah diregistrasikan sebelumnya secara manual
        unregisterReceiver(downloadReceiver)
        binding = null

        /*
        Berikut adalah 3 poin penting yang menjadi kesimpulan dari proses manual di atas:

        - Registrasikan sebuah obyek BroadcastReceiver pada komponen aplikasi seperti Activity dan Fragment
        dan tentukan action/event apa yang ingin didengar/direspon.
        -Lakukan proses terkait pada metode onReceive() ketika event atau action yang dipantau
        di-broadcast oleh komponen lain.
        -Jangan lupa untuk mencopot pemasangan obyek receiver sebelum komponen tersebut dihancurkan
        atau dimatikan.
        * */
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            when(PackageManager.PERMISSION_GRANTED){
                grantResults[0] -> Toast.makeText(this, "Sms Receiver Permission Diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Sms Receiver Permission Ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
        private const val SMS_REQUEST_CODE = 101
    }
}