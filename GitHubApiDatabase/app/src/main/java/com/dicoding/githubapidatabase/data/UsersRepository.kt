package com.dicoding.githubapidatabase.data

import androidx.lifecycle.LiveData
import com.dicoding.githubapidatabase.data.local.UsersDao
import com.dicoding.githubapidatabase.data.local.UsersEntity

class UsersRepository private constructor(
    private val usersDao: UsersDao
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
        ): UsersRepository =
            instance ?: synchronized(this){
                instance ?: UsersRepository(usersDao)
            }.also { instance = it }
    }

}