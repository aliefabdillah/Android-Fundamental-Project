package com.dicoding.githubapidatabase.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapidatabase.data.api.*
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    /* Backing property */
    private val _listUserData = MutableLiveData<List<Users>>()
    val listUserData: LiveData<List<Users>> = _listUserData

    private val _listFollowersData = MutableLiveData<List<Users>>()
    val listFollowersData: LiveData<List<Users>> = _listFollowersData

    private val _listFollowingData = MutableLiveData<List<Users>>()
    val listFollowingData: LiveData<List<Users>> = _listFollowingData

    private val _detailUsers = MutableLiveData<UsersDetailsResponse>()
    val detailUsers: LiveData<UsersDetailsResponse> = _detailUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<EventHandlerToast<String>>()
    val toastText: LiveData<EventHandlerToast<String>> = _toastText

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
                    if (response.body()?.totalCount == 0){
                        _toastText.value = EventHandlerToast("Username Not Found")
                    }else{
                        _listUserData.value = response.body()?.items
                    }
                }else {
                    Log.e(TAG, "OnFailure in Response Method: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure in Failure Method: ${t.message}")
            }

        })
    }

    fun getUserDetails(username : String) {
        _isLoading.value = true
        val clientDetails = GithubApiConfig.getApiService().getDetailsData(username)

        clientDetails.enqueue(object : retrofit2.Callback<UsersDetailsResponse> {
            override fun onResponse(
                call: Call<UsersDetailsResponse>,
                response: Response<UsersDetailsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUsers.value = response.body()
                }else {
                    Log.e(TAG, "OnFailure in Response Method Details: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure in Failure Method: ${t.message}")
            }

        })
    }

    fun getFolls(username : String, paramService: String) {
        _isLoading.value = true
        val client = GithubApiConfig.getApiService().getFollowersData(username, paramService)

        client.enqueue(object : retrofit2.Callback<List<Users>>{
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (paramService == "followers"){
                        _listFollowersData.value = response.body()
                    }else{
                        _listFollowingData.value = response.body()
                    }
                }else {
                    Log.e(TAG, "OnFailure in Response Method: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure in Failure Method: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}