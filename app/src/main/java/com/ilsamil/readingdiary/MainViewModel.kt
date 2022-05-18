package com.ilsamil.readingdiary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilsamil.readingdiary.fragments.HomeFragment
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.DataFormatException

class MainViewModel : ViewModel() {
    val mHeaderDate = MutableLiveData<Long>()
    val mCalendar = MutableLiveData<Calendar>()


    private var mCurrentTime : Long? = null

    val mTitle = MutableLiveData<String>()
    val mCalendarList = MutableLiveData<ArrayList<Any>>()

    val mCenterPosition : Int? = null


    fun setHeaderDate(headerDate : Long) {
        mHeaderDate.value = headerDate
    }

    fun setCalendar(calendar : Calendar) {
        mCalendar.value = calendar
    }


    fun setTitle(position : Int) {
        try {
            val item = mCalendarList.value?.get(position)
            if (item is Long) {
                setTitle(item)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun setTitle(time: Long) {
        mCurrentTime = time

        val formatter = SimpleDateFormat("yyyy년 MM월")
        val currentTime = System.currentTimeMillis()

        mTitle.value = formatter.format(currentTime)
    }

    fun initCalendarList() {
        val cal = GregorianCalendar()
        setCalendarList(cal)
    }


    fun setCalendarList(cal : GregorianCalendar) {
        setTitle(cal.timeInMillis)

        // 모든 타입을 받는 ArrayList
        val calendarList = ArrayList<Any>()

        for (i in -300 .. 300) {
            try {
                val calendar = GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + i, 1, 0, 0, 0)

                // 날짜 타입
                calendarList.add(calendar.timeInMillis)

                // 해당 월에 시작하는 요일에 -1을 하여 빈칸 흭득
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1

                //해당 월에 마지막 요일
                val max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

                for(j in 0 .. dayOfWeek) {
                    calendarList.add(0)
                }

                for(j in 0 .. max) {
                    calendarList.add(GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j))
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }

        mCalendarList.value = calendarList

    }
}