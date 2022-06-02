package com.ilsamil.readingdiary.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyBook (
    var name : String,
    var imgUrl : String,
    var lastDate : String,
    var edPage : Int,
    var curPage : Int,
) {
    @PrimaryKey(autoGenerate = true) var bookId : Int = 0
}