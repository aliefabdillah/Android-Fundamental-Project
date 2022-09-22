package com.dicoding.mynotesapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.mynotesapp.db.DatabaseContract.NoteColums.Companion.TABLE_NAME
import com.dicoding.mynotesapp.db.DatabaseContract.NoteColums

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbnoteapp"

        private const val DATABASE_VERSION = 1

        private const val  SQL_CREATE_TABLE_NOTE ="CREATE TABLE $TABLE_NAME" +
                "(${NoteColums._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${NoteColums.TITLE} TEXT NOT NULL," +
                " ${NoteColums.DESCRIPTION} TEXT NOT NULL," +
                " ${NoteColums.DATE} TEXT NOT NULL)"

        private const val SQL_DELETE_TABLE_NOTE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        db.execSQL(SQL_DELETE_TABLE_NOTE)
        onCreate(db)
    }
}

/*
* Tanggung jawab utama dari kelas di atas adalah menciptakan database dengan tabel yang dibutuhkan
* dan handle ketika terjadi perubahan skema pada tabel (terjadi pada metode onUpgrade()).
* Nah, di kelas ini kita menggunakan variabel yang ada pada DatabaseContract untuk mengisi kolom
* nama tabel. Begitu juga dengan kelas-kelas lainnya nanti. Dengan memanfaatkan kelas contract,
* maka akses nama tabel dan nama kolom tabel menjadi lebih mudah. */