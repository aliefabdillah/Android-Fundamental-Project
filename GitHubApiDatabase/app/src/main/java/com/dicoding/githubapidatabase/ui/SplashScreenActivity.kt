package com.dicoding.githubapidatabase.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubapidatabase.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.githubLogo.alpha = 0f
        binding.githubLogo.animate().setDuration(5000).alpha(1f).withEndAction {
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}