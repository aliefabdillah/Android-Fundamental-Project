package com.dicoding.githubapidatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        val favoriteMenu = menu.findItem(R.id.favoriteMenu)
        favoriteMenu.isVisible = false

        val settingMenu = menu.findItem(R.id.settingMenu)
        settingMenu.setOnMenuItemClickListener {
            val  i = Intent(this, SettingActivity::class.java)
            startActivity(i)
            true
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}