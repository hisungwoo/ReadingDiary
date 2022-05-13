package com.ilsamil.readingdiary.model

import com.google.gson.annotations.SerializedName

data class SearchBookDto (
    @SerializedName("documents") val bookInfo : List<Books>,
    @SerializedName("meta") val meta : Meta
    )