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
    var edPage : Int,
    var curPage : Int,
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var bookId : Int = 0
}