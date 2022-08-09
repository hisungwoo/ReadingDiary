package com.ilsamil.readingdiary.models

import com.google.gson.annotations.SerializedName
import com.ilsamil.readingdiary.data.db.entity.Meta

data class SearchBookDto (
    @SerializedName("documents") val bookInfo : List<Books>,
    @SerializedName("meta") val meta : Meta
    )