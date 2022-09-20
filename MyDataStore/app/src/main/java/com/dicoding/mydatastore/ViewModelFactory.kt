package com.dicoding.mydatastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/*
* Class ini digunakan agar kita bisa membuat viewModel yang memiliki contructor secara langsung
* Untuk itu, kita perlu membuat class yang extend ke ViewModelProvider untuk bisa memasukkan
* constructor ke dalam ViewModel.
*
* Dengan ViewModelFactory, Anda dapat memasukkan constructor dengan cara mengirim data ke
* ViewModelFactory terlebih dahulu, baru setelah itu dikirimkan ke ViewModel pada fungsi create. */
class ViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}