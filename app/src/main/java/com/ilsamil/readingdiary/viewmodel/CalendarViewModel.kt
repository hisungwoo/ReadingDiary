package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.CalendarDay
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    val calReadList = MutableLiveData<ArrayList<CalendarDay>>()
    private val db = AppDatabase.getInstance(application.applicationContext)

    private fun setCalendarList(date: LocalDate, readingList: List<String>) {
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
        for (i in 1..42) {
            // 비어있는 일자
            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add(calEmpty)
            } else {
                if (readingList.contains((i - dayOfWeek).toString())) {
                    // 독서 기록 없음
                    dayList.add(CalendarDay(false, year, month, (i - dayOfWeek).toString(), true))
                } else {
                    // 독서 기록 있음
                    dayList.add(CalendarDay(false, year, month, (i - dayOfWeek).toString(), false))
                }
            }
        }

        var isEmptyCheck = false
        for (i in 0..6) {
            if (dayList[i].isEmpty) {
                isEmptyCheck = true
            } else {
                isEmptyCheck = false
                break
            }
        }
        if (isEmptyCheck) for (i in 0..6) dayList.removeAt(0)
        calReadList.postValue(dayList)
    }


    fun setCalendar(selectedDate: LocalDate) {
        val year = selectedDate.year.toString()
        val month = selectedDate.monthValue.toString()

        viewModelScope.launch {
            val readingList = db!!.readingDao().selectReadingDate(year, month)
            setCalendarList(selectedDate, readingList)
        }
    }

    suspend fun getCalInfo(item: CalendarDay): ReadingDay {
        return withContext(viewModelScope.coroutineContext) {
            db!!.readingDao().selectReadingDay(item.year, item.month, item.day)
        }
    }

    suspend fun getImgUrl(readingDay: ReadingDay): String {
        return withContext(viewModelScope.coroutineContext) {
            db!!.myBookDao().selectImgUrl(readingDay.book)
        }
    }

    fun removeReadingDay(removeDate: CalendarDate, selectedDate: LocalDate) {
        viewModelScope.launch {
            val isComplete = db!!.readingDao().deleteReadingDay(removeDate.year, removeDate.month, removeDate.day)
            if (isComplete >= 1) {
                val year = selectedDate.year.toString()
                val month = selectedDate.monthValue.toString()

                val readingList = db.readingDao().selectReadingDate(year, month)
                setCalendarList(selectedDate, readingList)
            }
        }
    }
}

data class CalendarDate(
    var year: String,
    var month: String,
    var day: String
)