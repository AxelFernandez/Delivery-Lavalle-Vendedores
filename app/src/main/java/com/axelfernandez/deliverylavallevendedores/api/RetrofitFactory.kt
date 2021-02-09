package com.axelfernandez.deliverylavallevendedores.api

import android.content.Context
import com.axelfernandez.deliverylavallevendedores.BuildConfig
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory {
    private fun getRetrofit(context: Context):Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.hostname + "/sellers/") // change this IP for testing in Gradle files
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient(context))
            .build()
    }
    private fun getClient(context: Context): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor {chain->
            val requestBuilder = chain.request().newBuilder()
            if (!chain.request().url().encodedPath().contains("google")) {
                requestBuilder.addHeader("Authorization", "Bearer %s".format(LoginUtils.getUserFromSharedPreferences(context).token))
            }
            chain.proceed(requestBuilder.build())
        }
        return client.build()

    }
    fun<T> buildService(service: Class<T>, context: Context): T{
        return getRetrofit(context).create(service)
    }
}