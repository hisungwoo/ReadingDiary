package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddReadingViewModel(application : Application) : AndroidViewModel(application) {
    val isEdit = MutableLiveData<Boolean>()

    val editReadingDay = MutableLiveData<ReadingDay>()
    val editImg = MutableLiveData<String>()
    val selBooks = MutableLiveData<List<MyBook>>()

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "database-app")
        .build()


    fun setEdit(year : String, month : String, day : String) {
        viewModelScope.launch {
            editReadingDay.value = db.readingDao().selectReadingDay(year, month, day)
        }
    }

    fun setEditImg(name : String) {
        viewModelScope.launch {
            editImg.value = db.myBookDao().selectImgUrl(name)
        }
    }

    fun setSelectBook() {
        viewModelScope.launch {
            val books = db.myBookDao().selectMyBook()
            if(books != null) {
                for (i in books.indices) {
                    val curPage = db.readingDao().selectMaxRead(books[i].name)

                    if(curPage == null) books[i].curPage = 0
                    else  books[i].curPage = curPage.readEd!!
                }
                selBooks.value = books
            }
        }
    }

    fun addReadingDiary(data : ReadingDay) {
        viewModelScope.launch {
            db.readingDao().insertReadingDay(data)
        }
    }

    fun updateReadingDay(readingDay : ReadingDay) {
        viewModelScope.launch {
            db.readingDao().updateReadingDay(readingDay.year,
                readingDay.month, readingDay.day, readingDay.book,
                readingDay.readSt.toString(),
                readingDay.readEd.toString(),
                readingDay.maxPage.toString())
        }
    }


}