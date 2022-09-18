package com.dicoding.githubapiapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    // mengambil data saat tombol search ditekan
    @GET("search/users")
//    @Headers("Authorization: token <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>")
    fun getSearchData(
        @Query("q") login : String
    ): Call <GithubResponse>

    @GET("users/{login}")
    fun getDetailsData(
        @Path("login") login: String
    ): Call<UsersDetailsResponse>
}