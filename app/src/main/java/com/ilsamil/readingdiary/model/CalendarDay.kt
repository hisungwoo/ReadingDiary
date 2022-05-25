package com.ilsamil.readingdiary.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CalendarDay (
    var isEmpty : Boolean,
    var year : String,
    var month : String,
    var day : String,
    var isRead : Boolean
) : Parcelable