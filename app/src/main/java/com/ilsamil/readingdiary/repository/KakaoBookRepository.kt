package com.ilsamil.readingdiary.repository

import com.ilsamil.readingdiary.models.SearchBookDto
import com.ilsamil.readingdiary.network.RetrofitApi

class KakaoBookRepository {

    // 싱글톤 패턴
    companion object {
        private var instance: KakaoBookRepository? = null
        fun getInstance(): KakaoBookRepository? {
            if (instance == null) {
                instance = KakaoBookRepository()
            }
            return instance
        }
    }

    suspend fun getBookInfo(SearchText : String): SearchBookDto? {
        val response = RetrofitApi.bookInterface.getBookInfo(SearchText, "accuracy", 50, "title")
        return if (response.isSuccessful) response.body() else null
    }
}

