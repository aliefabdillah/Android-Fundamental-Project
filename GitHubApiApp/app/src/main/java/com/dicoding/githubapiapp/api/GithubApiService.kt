package com.dicoding.githubapiapp.api

import android.provider.Contacts.SettingsColumns.KEY
import com.dicoding.githubapiapp.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    // mengambil data saat tombol search ditekan
//    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("search/users")
    fun getSearchData(
        @Query("q") login : String
    ): Call <GithubResponse>

    @GET("users/{login}")
    fun getDetailsData(
        @Path("login") login: String
    ): Call<UsersDetailsResponse>

    @GET("users/{login}/{folls}")
    fun getFollowersData(
        @Path("login") login: String,
        @Path("folls") params: String
    ): Call<List<FollsResponseItem>>
}