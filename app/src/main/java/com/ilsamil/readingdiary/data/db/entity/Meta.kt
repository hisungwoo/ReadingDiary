package com.ilsamil.readingdiary.data.db.entity

import androidx.room.Entity


@Entity
data class Meta (
    var totalCount : Int,
    var pageableCount : Int,
    var isEnd : Boolean
    )