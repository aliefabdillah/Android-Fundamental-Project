package com.dicoding.restoranreview

import retrofit2.Call
import retrofit2.http.*

/*
* API Service merupakan interface yang berisi kumpulan endpoint yang digunakan pada sebuah aplikasi*/
interface ApiService {
    /*
    * endpoint Get digunakan untuk mengambil data dengan menganti variabel {id} menggunakan @path
    * sehingga dapat mengakses detail suatu restoran dengan
    * URL https://restaurant-api.dicoding.dev/detail/uewq1zg2zlskfw1e867.*/
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>


    /*
    * endpoint post digunakan untuk mengirim data, dapat juga menambahkan header untuk menyematkan
    * token jika api membutuhkan otorisasi.
    *
    * untuk mengiriim data harus memakai anotasi @FormUrlEncoded untuk mengirimkan data dengan
    * menggunakan @Field. Pastikan key yang dimasukkan pada @Field harus sama dengan field
    * yang ada pada API*/
    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("review") review : String
    ): Call<PostReviewResponse>
}