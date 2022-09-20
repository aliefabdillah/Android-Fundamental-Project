package com.dicoding.mydatastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

/*
* Catatan:
Kode yang sudah Anda buat sebelumnya dapat berjalan hanya pada Activity itu saja. Misal Anda
* punya aplikasi dengan urutan Activity A ke Activity B. Jika kode untuk mengubah tema ada
* di Activity B, maka Activity A tidak akan ikut berubah. Maka, untuk mengatasi ini, maka Anda
* harus membaca data dari preference dan mengganti tema pada Activity A (Activity yang pertama
* kali dibuka ketika launch).
*/

//Membuat Instance data store
/*
* Kita membuat extension function dengan nama dataStore dengan menggunakan property delegation
* by preferencesDataStore  kode ini dibuat di top-level supaya menjadi Singleton yang cukup dipanggil sekali.
* “settings” adalah string yang digunakan untuk memberi nama file DataStore.*/
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        /*
        * penggunaan fungsi instance untuk data store dikarenakan datastore harus berupa
        * singleton object yang ada satu instace yang dapat digunakan dibanyak tempat*/
        val pref = SettingPreferences.getInstance(dataStore)

        /*
        * Ketika mainAcitivity membutuhkan viewmodel maka perlu memanggil instance kelas ViewModelFactory
        * terlebih dahulu*/
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}