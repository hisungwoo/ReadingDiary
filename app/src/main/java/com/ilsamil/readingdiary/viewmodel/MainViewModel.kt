package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.CalendarDay
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class MainViewModel(application : Application) : AndroidViewModel(application) {
    val calReadList = MutableLiveData<ArrayList<CalendarDay>>()

    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "database-app")
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

        val year = date.year.toString()
        val month = date.monthValue.toString()

        val calEmpty = CalendarDay(true, "", "", "", false)
        for(i in 1 .. 42) {
            if(i <= dayOfWeek || i > lastDay + dayOfWeek) dayList.add(calEmpty)
            else {
                if (readingList.contains((i-dayOfWeek).toString())) {
                    dayList.add(CalendarDay(false, year, month,(i-dayOfWeek).toString(), true))
                } else {
                    dayList.add(CalendarDay(false, year, month, (i-dayOfWeek).toString(), false))
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

    fun getBooks() : List<MyBook> {
        return db.myBookDao().selectMyBook()
    }

    fun getCurPage(book : String) : ReadingDay {
        return db.readingDao().selectMaxRead(book)
    }

    fun getReadingDay(year : String, month : String, day : String) : ReadingDay {
        return db.readingDao().selectReadingDay(year, month, day)
    }

    fun removeReadingDay(year : String, month : String, day : String) : Int {
        return db.readingDao().deleteReadingDay(year, month, day)
    }

    fun updateReadingDay(readingDay : ReadingDay) {
        return db.readingDao().updateReadingDay(readingDay.year, readingDay.month, readingDay.day, readingDay.book, readingDay.readSt.toString(), readingDay.readEd.toString(), readingDay.maxPage.toString())
    }

    fun getImgUrl(name : String) : String {
        return db.myBookDao().selectImgUrl(name)
    }

}