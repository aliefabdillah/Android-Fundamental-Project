package com.dicoding.mybroadcastreceiver

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionManager {
    fun check(activity: MainActivity, permission: String, requestCode: Int){
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }
    }
}