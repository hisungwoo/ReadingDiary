package com.ilsamil.readingdiary.model

import androidx.room.Entity


@Entity
data class Meta (
    var totalCount : Int,
    var pageableCount : Int,
    var isEnd : Boolean
    )