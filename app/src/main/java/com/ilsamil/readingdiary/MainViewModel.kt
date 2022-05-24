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
import com.ilsamil.readingdiary.model.CalendarDay
import com.ilsamil.readingdiary.model.ReadingDay
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import java.util.zip.DataFormatException

class MainViewModel(application : Application) : AndroidViewModel(application) {
    val calReadList = MutableLiveData<ArrayList<CalendarDay>>()

    private val db = Room.databaseBuilder(application, ReadingDatabase::class.java, "database-reading")
        .allowMainThreadQueries()
        .build()

    fun setCalendarList(date : LocalDate, readingList : List<String>) {
        val dayList = ArrayList<CalendarDay>()
        val yearMonth = YearMonth.from(date)

        // 해당 월 첫번째 날
        val firstDay = date.withDayOfMonth(1)

        // 첫번째 요일
        val dayOfWeek = firstDay.dayOfWeek.value

        // 해당 월 마지막 일
        val lastDay = yearMonth.lengthOfMonth()

//        Log.d("ttest", "readingList = $readingList")
//        Log.d("ttest", readingList.contains("21").toString())

        val calEmpty = CalendarDay(true, "", false)
        for(i in 1 .. 42) {
            if(i <= dayOfWeek || i > lastDay + dayOfWeek) dayList.add(calEmpty)
            else {
                if(readingList.contains((i-dayOfWeek).toString())) {
                    dayList.add(CalendarDay(false, (i-dayOfWeek).toString(), true))
                } else {
                    dayList.add(CalendarDay(false, (i-dayOfWeek).toString(), false))
                }
            }
        }

        var isEmptyCheck = false
        for (i in 0..6) {
            if(dayList[i].isEmpty) isEmptyCheck = true
            else {
                isEmptyCheck = false
                break
            }
        }
        if(isEmptyCheck) for (i in 0 .. 6) dayList.removeAt(0)

        calReadList.postValue(dayList)
    }


    fun getReadingDate(year : String, month : String) : List<String> {
        return db.readingDao().selectReadingDate(year, month)
    }

    fun addReadingDiary(data : ReadingDay) : Long {
        return db.readingDao().insertReadingDay(data)
    }







}