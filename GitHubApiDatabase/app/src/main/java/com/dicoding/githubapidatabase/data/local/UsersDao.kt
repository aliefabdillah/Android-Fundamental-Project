package com.dicoding.githubapidatabase.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersDao {
    @Query("SELECT * FROM usersFavorite")
    fun getUser(): LiveData<List<UsersEntity>>

    @Query("SELECT EXISTS(SELECT * FROM usersFavorite WHERE login = :login)")
    fun isUserFavorited(login: String): LiveData<Boolean>

    //query dengan coroutine
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(user: UsersEntity)

    @Update
    suspend fun updateUser(user: UsersEntity)

    @Query("DELETE FROM usersFavorite WHERE login = :login")
    suspend fun deleteFavorite(login: String)

}