package com.dicoding.myviewmodel

import androidx.lifecycle.ViewModel

/*
* Dengan menambahkan turunan kelas ViewModel ke kelas MainViewModel, itu menandakan
* bahwa kelas tersebut menjadi kelas ViewModel. Segala sesuatu yang ada di kelas tersebut
* akan terjaga selama Activity masih dalam keadaan aktif. Pada kelas MainViewModel, nilai
* dari result akan selalu dipertahankan selama MainViewModel masih terikat dengan Activity.*/
class MainViewModel : ViewModel() {
    var result = 0

    fun calculate(width: String, length: String, height : String){
        result = width.toInt() * length.toInt() * height.toInt()
    }
}