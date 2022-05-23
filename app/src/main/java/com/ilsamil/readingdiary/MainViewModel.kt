package com.ilsamil.readingdiary

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.ilsamil.readingdiary.database.ReadingDatabase
import com.ilsamil.readingdiary.fragments.HomeFragment
import com.ilsamil.readingdiary.model.ReadingDay
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.DataFormatException

class MainViewModel(application : Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, ReadingDatabase::class.java, "database-reading")
        .allowMainThreadQueries()
        .build()

    val mHeaderDate = MutableLiveData<Long>()
    val mCalendar = MutableLiveData<Calendar>()


    fun getReadingDate(year : String, month : String) : List<String> {
        return db.readingDao().selectReadingDate(year, month)
    }













}