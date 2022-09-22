package com.dicoding.mynoteapps.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.mynoteapps.database.Note
import com.dicoding.mynoteapps.repository.NoteRepository

class NoteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mNotesRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note){
        mNotesRepository.insert(note)
    }

    fun update(note: Note){
        mNotesRepository.update(note)
    }

    fun delete(note: Note){
        mNotesRepository.delete(note)
    }
}