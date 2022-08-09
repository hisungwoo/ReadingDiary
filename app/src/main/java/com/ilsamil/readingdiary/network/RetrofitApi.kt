package com.ilsamil.readingdiary.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    private const val BASE_URL = "https://dapi.kakao.com"

    private fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val bookInterface : BookInterface by lazy {
        retrofit().create(BookInterface::class.java)
    }
}