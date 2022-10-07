package com.dicoding.githubapidatabase.di

import android.content.Context
import com.dicoding.githubapidatabase.data.UsersRepository
import com.dicoding.githubapidatabase.data.local.UsersDatabase
import com.dicoding.githubapidatabase.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UsersRepository{
        val database = UsersDatabase.getInstance(context)
        val dao = database.usersDao()
        val appExecutors = AppExecutors()
        return UsersRepository.getInstance(dao, appExecutors)
    }
}