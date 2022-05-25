package com.ilsamil.readingdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadingDay (
    var year : String,
    var month : String,
    var day : String,
    var isRead : Boolean,
    var book : String,
    var stPage : Int,
    var edPage : Int,
    var curPage : Int
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}