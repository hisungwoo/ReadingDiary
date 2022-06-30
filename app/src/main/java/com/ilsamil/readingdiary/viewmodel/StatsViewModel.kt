package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,"database-app")
        .build()

    val statsReadCnt = MutableLiveData<Int>()
    val statsBookCnt = MutableLiveData<Int>()
    val finishBook = MutableLiveData<List<String>>()

    fun setReadCnt() {
        viewModelScope.launch {
            statsReadCnt.value = db.readingDao().selectReadingCount()
        }
    }

    fun setBookCnt() {
        viewModelScope.launch {
            statsBookCnt.value = db.readingDao().selectFinishRead().size
        }
    }

    fun getFinishBook() {
        viewModelScope.launch {
            val readingDays = db.readingDao().selectFinishRead()
            val items = ArrayList<String>()
            for (i in readingDays) {
                val item = db.myBookDao().selectReadingBook(i.book)
                if (item != null) {
                    items.add(item.name)
                }
            }

            //test
            items.add("아프니까 청춘이다")
            items.add("코틀린 강의")
            items.add("MVVM패턴 전문가")
            items.add("면접의 기술1")
            items.add("안드로이드 개발자 정석2")
            items.add("룬의 아이들3")
            items.add("연을 쫓는 아이들")
            items.add("아프니까 청춘이다")
            items.add("코틀린 강의")
            items.add("MVVM패턴 전문가")
            items.add("면접의 기술")
            items.add("안드로이드 개발자 정석")
            items.add("룬의 아이들")
            items.add("연을 쫓는 아이들")
            items.add("아프니까 청춘이다")
            items.add("코틀린 강의")
            items.add("MVVM패턴 전문가")
            items.add("면접의 기술")
            items.add("안드로이드 개발자 정석")
            items.add("룬의 아이들")
            items.add("연을 쫓는 아이들")
            items.add("아프니까 청춘이다")
            items.add("코틀린 강의")
            items.add("MVVM패턴 전문가")
            items.add("면접의 기술")
            items.add("안드로이드 개발자 정석")
            items.add("룬의 아이들")
            items.add("연을 쫓는 아이들")
            items.add("아프니까 청춘이다")
            items.add("코틀린 강의")
            items.add("MVVM패턴 전문가")
            items.add("면접의 기술")
            items.add("안드로이드 개발자 정석")
            items.add("룬의 아이들")
            items.add("연을 쫓는 아이들")


            finishBook.value = items
        }
    }

}