package com.ilsamil.readingdiary.repository

import com.ilsamil.readingdiary.data.remote.model.SearchBookDto
import com.ilsamil.readingdiary.network.BookInterface
import javax.inject.Inject

class KakaoBookRepository @Inject constructor(
    private val retrofitInstance : BookInterface
) {
    suspend fun getBookInfo(SearchText : String): SearchBookDto? {
        val response = retrofitInstance.getBookInfo(SearchText, "accuracy", 50, "title")
        return if (response.isSuccessful) response.body() else null
    }
}

