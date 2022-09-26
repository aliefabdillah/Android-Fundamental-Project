package com.dicoding.mybroadcastreceiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.mybroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)

    }

    var requstPermissionLaucher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Sms Receiver permission diterima", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Sms Receiver Permission ditolak", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_permission -> requstPermissionLaucher.launch(android.Manifest.permission.RECEIVE_SMS)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}