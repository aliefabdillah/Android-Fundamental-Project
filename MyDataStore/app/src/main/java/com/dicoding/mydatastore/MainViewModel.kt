package com.dicoding.mydatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences): ViewModel() {

    //method mengambil data dari preferences
    fun getThemeSettings(): LiveData<Boolean>{
        /*
        * karena data kembalian dari getThemeSetting pada preference adalah Flow maka
        * perlu diubah menjadi liveData menggunakan method asLiveData()*/
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        //untuk menjalankan method suspend pada SaveThemeSetting perlu menggunakan launch karena fungsi tersbut berada pada coroutine
        /*
        * viewModelScope merupakan scope yang sudah disediakan library lifecycle-viewmodel-ktx
        * untuk menjalankan coroutine pada ViewModel yang sudah aware dengan lifecycle.
        * Dengan begitu instance coroutine akan otomatis dihapus ketika ViewModel dibersihkan
        * sehingga aplikasi tidak mengalami memory leak (kebocoran memori).*/
        viewModelScope.launch { pref.saveThemeSetting(isDarkModeActive) }
    }
}