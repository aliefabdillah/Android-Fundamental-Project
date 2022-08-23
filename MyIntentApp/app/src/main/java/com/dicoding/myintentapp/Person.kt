package com.dicoding.myintentapp

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
* Penggunaan Parcelize untuk mempersingkat kode Parcelable di data class, akan tetapi harus menambahkan
* kode id 'kotlin-parcelize' di bulid.gradle(module: ...)
*
* Parcelize hanya dapat digunakan di bahasa pemrograman kotlin*/
@Parcelize
data class Person(
    val name: String?,
    val age: Int?,
    val email: String?,
    val city: String?
): Parcelable