package com.dicoding.githubapidatabase.di

import android.content.Context
import com.dicoding.githubapidatabase.data.UsersRepository
import com.dicoding.githubapidatabase.data.local.UsersDatabase

object Injection {
    fun provideRepository(context: Context): UsersRepository{
        val database = UsersDatabase.getInstance(context)
        val dao = database.usersDao()
        return UsersRepository.getInstance(dao)
    }
}