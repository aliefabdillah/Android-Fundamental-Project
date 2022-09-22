package com.dicoding.mynoteapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* Anotasi @Database menjadikan selurh turuan kelas RoomDatabase menjadi sebuah RoomDatabase*/
@Database(entities = [Note::class], version = 2)
abstract class NoteRoomDatabase:RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE : NoteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteRoomDatabase {
            if(INSTANCE == null){
                synchronized(NoteRoomDatabase::class.java){
                    /*Kode ini dipakai untuk memabngun database aplikasi dengan
                    * nama database note_database*/
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteRoomDatabase::class.java, "note_database").build()
                }
            }

            return INSTANCE as NoteRoomDatabase
        }
    }
}
