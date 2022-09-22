package com.dicoding.mynoteapps.database

import androidx.lifecycle.LiveData
import androidx.room.*

/*
* Interface berisi Aksi CRUD
*
* Anotasi Dao akan menjadi sebuah kelas DAO Otomatis
* */
@Dao
interface NoteDao {
    //query insert
    /*
    * dangkan kode OnConflictStrategy.IGNORE digunakan jika data yang dimasukkan sama, maka dibiarkan saja.*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    //query update
    @Update
    fun update(note: Note)

    //query delete
    @Delete
    fun delete(note: Note)

    //query select by id
    @Query("Select * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}