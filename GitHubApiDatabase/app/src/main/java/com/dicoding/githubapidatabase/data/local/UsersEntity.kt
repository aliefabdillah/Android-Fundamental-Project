package com.dicoding.githubapidatabase.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "usersFavorite")
@Parcelize
class UsersEntity(
    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    val login: String,

    @field:ColumnInfo(name = "avatar")
    val avatarUrl: String,
    
    @field:ColumnInfo(name = "gitHubId")
    val gitHubId: Int,

    @field:ColumnInfo(name = "html_url")
    val html_url: String,

    @field:ColumnInfo(name = "favorited")
    val isFavorited: Boolean,
): Parcelable