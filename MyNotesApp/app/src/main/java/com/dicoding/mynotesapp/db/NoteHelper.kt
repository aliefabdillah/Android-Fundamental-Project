package com.dicoding.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dicoding.mynotesapp.db.DatabaseContract.NoteColums
import java.sql.SQLException
import kotlin.jvm.Throws

class NoteHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = NoteColums.TABLE_NAME

        //method untuk inisialiasi database
        /*Singelton variabel untuk proses mengambil data secara asynchronus*/
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            // synchronized di sini dipakai untuk menghindari duplikasi instance di semua Thread,
            // karena bisa saja kita membuat instance di Thread yang berbeda.
            INSTANCE ?: synchronized(this){
                INSTANCE ?: NoteHelper(context)
            }
    }

    //method open dan close database
    //open connection
    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    //close connection
    fun close(){
        databaseHelper.close()
        if (database.isOpen){
            database.close()
        }
    }

    //Method CRUD

    //SELECT ALL QUERY / query untuk mengambil semua data
    /*
    * Kembalian fungsi ini berupa cursor dan jika ingin dipilkan di view kita harus
    * mengubahnya ke bentuk ArrayList dengan bantuan Object MappingHelper*/
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${NoteColums._ID} ASC"
        )
    }

    //SELECT BY ID / query dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            " = ?",
            arrayOf(id),
            null,
            null,
            null
        )
    }

    //INSERT DATA / query menyimpan data ke tabel
    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE, null, values)
    }

    //UPDATE DATA / query memperbarui data di dalam tabel
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${NoteColums._ID} = ?", arrayOf(id))
    }

    //DELETE DATA / query menghapus data
    fun delete(id: String): Int{
        return database.delete(DATABASE_TABLE,"${NoteColums._ID} = $id", null)
    }
}