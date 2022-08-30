package com.dicoding.myrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    var name: String,
    var description: String,
    var photo: String           //menjadi string karena foto berupa string
) : Parcelable