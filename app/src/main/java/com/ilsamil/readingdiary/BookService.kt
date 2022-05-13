package com.ilsamil.readingdiary

import com.ilsamil.readingdiary.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookService {
    @GET("/v3/search/book?target=title")
    @Headers("Authorization:KakaoAK 2e8248c1432bf2d2e338714788407277")
    fun getBookInfo(
        @Query("query") query : String,
        @Query("sort") sort : String,
        @Query("size") size : Int,
        @Query("target") target : String
    ) : Call<SearchBookDto>
}