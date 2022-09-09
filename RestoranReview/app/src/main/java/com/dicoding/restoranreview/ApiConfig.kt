package com.dicoding.restoranreview

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restaurant-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

/*
* Saat menggunakan logging interceptor untuk aplikasi yang sudah dipublikasikan, pastikan kembali
* pesan log hanya akan tampil pada mode debug. Saat informasi sensitif dapat mudah lihat di jendela
* logcat dan ini membuat penerapan security menyebabkan vulnerability di mana data yang tampil
* dapat dimanfaatkan oleh pihak yang tidak bertanggung jawab.*/