package com.dicoding.mydatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    //variabel key untuk menyimpan data ke preferences DataStore
    /*
    * Key ini harus unik supaya tidak tercampur dengan data lain. */
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    /* Method untuk membaca data Preferences DataStore
    * Untuk mengambil data yang sudah disimpan, kita menggunakan fungsi map pada variabel data.
    * Pastikan Anda menggunakan key yang sama dengan saat Anda menyimpannya untuk mendapatkan
    * data yang tepat.*/
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false             //elvis operator jika datanya masih kosong/null
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        /*
        * Untuk menyimpan data menggunakan fungsi lambda edit denga parameter yang berupa
        * mutablePreferences. untuk Untuk mengubah data, Anda perlu menentukan key data yang
        * ingin diubah dan isi datanya. Selain itu, karena edit adalah suspend function, maka
        * ia harus dijalankan di coroutine atau suspend function juga.*/
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        /*
        * kontruktor berfungsi sebagai singleton*/
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            //kode synchronized dipakai untuk membuat semua thread tersinkronisasi saat instance datastore
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
        /*
        * Fungsi dari Singleton yaitu dapat menciptakan satu instance saja di dalam JVM,
        * sehingga tidak memakan memori yang terbatas. Jadi, ketika Activity A memanggil
        * SettingPreferences, kelas itu akan membuat instance dalam bentuk volatile. Volatile
        * adalah keyword yang digunakan supaya nilai pada suatu variabel tidak dimasukkan ke
        * dalam cache. Kemudian jika Activity B memanggil fungsi ini, kelas tersebut akan
        * memeriksa apakah instance-nya sudah ada. Jika tidak null, sistem akan mengembalikan
        * instance tersebut pada Activity B, tidak membuat instance baru.  */
    }
}