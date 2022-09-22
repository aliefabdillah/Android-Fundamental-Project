package com.dicoding.mynotesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
* Data kelas ini untuk merepresentasikan dat ayang tersimpan
* dan memberi kemudahan dalam menulis kode den lebih simpel dibandingkan harus mengolah data
* dengan objek Cursor*/
@Parcelize
data class Note(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
): Parcelable
