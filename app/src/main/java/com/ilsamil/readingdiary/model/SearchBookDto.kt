package com.ilsamil.readingdiary.model

import com.google.gson.annotations.SerializedName
import com.ilsamil.readingdiary.data.db.entity.Books

data class SearchBookDto (
    @SerializedName("documents") val bookInfo : List<Books>,
    @SerializedName("meta") val meta : Meta
    )