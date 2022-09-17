package com.dicoding.githubapiapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    /* Backing property */
    private val _listUserData = MutableLiveData<List<UserItems>>()
    val listUserData: LiveData<List<UserItems>> = _listUserData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findUser(username : String) {
        _isLoading.value = true
        val client = GithubApiConfig.getApiService().getSearchData(username)

        client.enqueue(object : retrofit2.Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUserData.value = response.body()?.items
                }else {
                    println(_listUserData.value)
                    Log.e(TAG, "OnFailure in Response Method: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure in Failure Method: ${t.message}")
            }

        })
    }

}