package com.ilsamil.readingdiary.repository

import com.ilsamil.readingdiary.data.remote.api.BookInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KakaoBookRepository {
    private val bookInterface : BookInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BookInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookInterface = retrofit.create(BookInterface::class.java)
    }

    suspend fun getBookInfo(SearchText : String) =
        bookInterface.getBookInfo(SearchText, "accuracy", 50, "title")

}