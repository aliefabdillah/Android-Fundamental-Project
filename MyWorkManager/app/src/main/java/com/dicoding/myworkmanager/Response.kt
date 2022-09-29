package com.dicoding.myworkmanager

import com.squareup.moshi.Json

data class Response(
    val id: Int,
    val name: String,
    @Json(name = "weather")             //untuk mengganti nama variabel tidak sesuai API
    val weatherList: List<Weather>,
    val main: Main,
)

data class Weather(
    val main: String,
    val description: String
)

data class Main(
    @Json(name = "temp")
    val temperature: Double
)
