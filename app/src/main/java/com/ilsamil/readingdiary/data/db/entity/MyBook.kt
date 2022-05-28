package com.ilsamil.readingdiary.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyBook (
    var name : String,
    var imgUrl : String,
    var stPage : Int,
    var edPage : Int,
    var isEnd : Boolean
) {
    @PrimaryKey(autoGenerate = true) var bookId : Int = 0
}