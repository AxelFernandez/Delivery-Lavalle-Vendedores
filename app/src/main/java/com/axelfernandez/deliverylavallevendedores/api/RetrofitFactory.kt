package com.axelfernandez.deliverylavallevendedores.api

import com.axelfernandez.deliverylavallevendedores.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl( BuildConfig.hostname+"/sellers/") // change this IP for testing in Gradle files
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}