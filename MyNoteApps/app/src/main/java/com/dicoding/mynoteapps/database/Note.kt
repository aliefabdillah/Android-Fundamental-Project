package com.dicoding.mynoteapps.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*
* Class Berisi Entitas Tabel Database*/
@Entity
@Parcelize
data class Note(
    /*
    * Menentukan primary key dapat menggunakan anotasi @PrimaryKey
    *
    * Kode @ColumnInfo digunakan untuk memberi nama column dari tabel. Jika tidak diberi nama,
    * maka default dari nama column adalah variable tersebut*/
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")            var id: Int = 0,

    @ColumnInfo(name = "title")         var title: String? = null,

    @ColumnInfo(name = "description")   var description: String? = null,

    @ColumnInfo(name = "date")          var date: String? = null
): Parcelable