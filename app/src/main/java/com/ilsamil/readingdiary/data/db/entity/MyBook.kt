package com.ilsamil.readingdiary.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MyBook (
    var name : String,
    var imgUrl : String,
    var lastDate : String,
    var curPage : Int,
    var edPage : Int,
    var introduce : String,
    var contentUrl : String
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var bookId : Int = 0
}