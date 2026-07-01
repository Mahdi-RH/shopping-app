package com.mahdi.assignment.shoppingapp.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun createRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }


}