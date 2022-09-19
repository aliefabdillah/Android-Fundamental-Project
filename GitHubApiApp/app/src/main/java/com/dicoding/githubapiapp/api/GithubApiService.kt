package com.dicoding.githubapiapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    // mengambil data saat tombol search ditekan
    @GET("search/users")
//    @Headers("Authorization: token encrypt (d43ba6a7b914cc8df515a20d29b3a3ed))
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