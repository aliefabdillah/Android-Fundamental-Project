package com.dicoding.githubapidatabase.data

import androidx.lifecycle.LiveData
import com.dicoding.githubapidatabase.data.api.GithubApiService
import com.dicoding.githubapidatabase.data.local.UsersDao
import com.dicoding.githubapidatabase.data.local.UsersEntity
import com.dicoding.githubapidatabase.utils.AppExecutors

class UsersRepository private constructor(
    private val usersDao: UsersDao,
    private val appExecutors: AppExecutors
) {

    fun getListFavoriteUsers(): LiveData<List<UsersEntity>> {
        return usersDao.getUser()
    }

    //cek state favorite
    fun getUsersFavoriteState(login: String) : LiveData<Boolean> {
        return usersDao.isUserFavorited(login)
    }

    //simpan data user favorite baru
    suspend fun insertFavoriteUser(user: UsersEntity){
        usersDao.insertUsers(user)
    }

    //hapus data user dari favorite
    suspend fun deleteFavoriteUser(login: String){
        usersDao.deleteFavorite(login)
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            usersDao: UsersDao,
            appExecutors: AppExecutors
        ): UsersRepository =
            instance ?: synchronized(this){
                instance ?: UsersRepository(usersDao, appExecutors)
            }.also { instance = it }
    }

}