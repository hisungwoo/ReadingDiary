package com.ilsamil.readingdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadingDay(
    var year: String,
    var month: String,
    var day: String,
    var book: String,
    var readSt: String?,
    var readEd: String?,
    var maxPage : String?
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}