package com.dicoding.mynotesapp.helper

import android.database.Cursor
import com.dicoding.mynotesapp.db.DatabaseContract
import com.dicoding.mynotesapp.entity.Note

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note>{
        val noteList = ArrayList<Note>()

        notesCursor?.apply {
            //movetonext digunakan untuk memindahkan data cursor ke baris selanjutnya (mirip iteration pada for)
            while (moveToNext()){
                //mengambil data coloum satu per satu dengan method getColumnInderOrThrow
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColums._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColums.TITLE))
                val desc = getString(getColumnIndexOrThrow(DatabaseContract.NoteColums.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColums.DATE))
                noteList.add(Note(id, title, desc, date))
            }
        }
        /*
        Catatan:
        Fungsi apply digunakan untuk menyederhanakan kode yang berulang. Misalnya notesCursor.geInt cukup
        ditulis getInt dan notesCursor.getColumnIndexOrThrow cukup ditulis getColumnIndexOrThrow
        */

        return noteList
    }
}