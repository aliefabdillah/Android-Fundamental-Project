package com.dicoding.githubapidatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubapidatabase.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}