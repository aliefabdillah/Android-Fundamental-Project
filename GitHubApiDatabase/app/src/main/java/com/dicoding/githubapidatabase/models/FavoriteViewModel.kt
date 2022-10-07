package com.dicoding.githubapidatabase.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapidatabase.data.UsersRepository
import com.dicoding.githubapidatabase.data.api.Users
import com.dicoding.githubapidatabase.data.local.UsersEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val _favoriteState =  MutableLiveData<Boolean>()
    var favoriteState: LiveData<Boolean> = _favoriteState

    fun getFavoriteUsers() = usersRepository.getListFavoriteUsers()

    fun checkUserInDb(login: String){
        favoriteState = usersRepository.getUsersFavoriteState(login)
    }

    fun saveFavorite(detailsUser: Users){
        val usersData = UsersEntity(
            detailsUser.login,
            detailsUser.avatarUrl,
            detailsUser.id,
            detailsUser.html_url,
            true
        )

        viewModelScope.launch {
            usersRepository.insertFavoriteUser(usersData)
        }
    }

    fun deleteFromFavorite(login: String){
        viewModelScope.launch {
            usersRepository.deleteFavoriteUser(login)
        }
    }
}