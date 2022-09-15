package com.dicoding.githubapiapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var username: String,
    var fullname: String,
    var repositories: String,
    var followers: String,
    var photo: Int,
    var following: String,
    var company: String,
    var location: String,
    var email: String
): Parcelable