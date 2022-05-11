package com.ilsamil.readingdiary.repository

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    companion object {
        const val FACL_BASE_URL = "http://apis.data.go.kr/B554287/DisabledPersonConvenientFacility/"
    }

    @GET("posts/{post}")
    fun getList(@Query("Rows") list : Int)



}