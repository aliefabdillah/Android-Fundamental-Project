package com.dicoding.githubapidatabase.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase(){
    //singleton global untuk instance DAO
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var instance: UsersDatabase? = null
        fun getInstance(context: Context): UsersDatabase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java, "UsersFavorite.db"
                ).build()
            }
    }
}