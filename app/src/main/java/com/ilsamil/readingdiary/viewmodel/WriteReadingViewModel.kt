package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.launch

class WriteReadingViewModel(application : Application) : AndroidViewModel(application) {
    val isEdit = MutableLiveData<Boolean>()

    val editReadingDay = MutableLiveData<ReadingDay>()
    val editImg = MutableLiveData<String>()
    val selBooks = MutableLiveData<List<MyBook>>()

    private val db = AppDatabase.getInstance(application.applicationContext)

    fun setEdit(year : String, month : String, day : String) {
        viewModelScope.launch {
            editReadingDay.value = db!!.readingDao().selectReadingDay(year, month, day)
        }
    }

    fun setImg(name : String) {
        viewModelScope.launch {
            editImg.value = db!!.myBookDao().selectImgUrl(name)
        }
    }

    fun setSelectBook() {
        viewModelScope.launch {
            val books = db!!.myBookDao().selectMyBook()
            val items = ArrayList<MyBook>()

            if(books != null) {
                for (i in books.indices) {
                    val curDay : ReadingDay? = db!!.readingDao().selectMaxRead(books[i].name)
                    if(curDay == null) {
                        val item = books[i]
                        item.curPage = 0
                        items.add(item)

                    } else if (curDay.readEd != books[i].edPage) {
                        val item = books[i]
                        item.curPage = curDay.readEd!!
                        books[i].lastDate = "${curDay.year}.${curDay.month}.${curDay.day}"
                        items.add(item)
                    }
                }
                selBooks.value = items
            }
        }
    }

    fun addReadingDiary(data : ReadingDay) {
        viewModelScope.launch {
            db!!.readingDao().insertReadingDay(data)
        }
    }

    fun updateReadingDay(readingDay : ReadingDay) {
        viewModelScope.launch {
            db!!.readingDao().updateReadingDay(readingDay.year,
                readingDay.month, readingDay.day, readingDay.book,
                readingDay.readSt.toString(),
                readingDay.readEd.toString(),
                readingDay.maxPage.toString())
        }
    }


}