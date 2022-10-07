package com.dicoding.githubapidatabase.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubapidatabase.data.UsersRepository
import com.dicoding.githubapidatabase.di.Injection

class ViewModelFactory private constructor(private val usersRepository: UsersRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}