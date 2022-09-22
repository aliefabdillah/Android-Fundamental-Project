package com.dicoding.mynoteapps.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mynoteapps.database.Note
import com.dicoding.mynoteapps.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    /*
    * Dengan memanggil getAllNotes(), Activity dengan mudah meng-observe data list notes dan bisa segera ditampilkan.*/
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}