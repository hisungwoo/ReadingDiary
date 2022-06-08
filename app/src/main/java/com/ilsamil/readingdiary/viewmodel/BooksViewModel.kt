package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BooksViewModel(application : Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
            application,
            AppDatabase::class.java,"database-app")
        .build()

    val bookList = MutableLiveData<List<MyBook>>()

    fun setCategoryAll() {
        viewModelScope.launch {
            val items = db.myBookDao().selectMyBook()
            for (i in items.indices) {
                val curDay : ReadingDay? = db.readingDao().selectMaxRead(items[i].name)
                if (curDay == null) items[i].curPage = 0
                else {
                    items[i].curPage = curDay.readEd!!
                    items[i].lastDate = "${curDay.year}.${curDay.month}.${curDay.day}"
                }
            }
            bookList.value = items
        }
    }

    fun setCategoryReading() {
        viewModelScope.launch {
            val myBook = db.myBookDao().selectMyBook()
            val items = ArrayList<MyBook>()
            for (i in myBook.indices) {
                val curDay : ReadingDay? = db.readingDao().selectMaxRead(myBook[i].name)
                if (curDay == null) {
                    val item = MyBook(myBook[i].name, myBook[i].imgUrl, "", myBook[i].edPage, 0, "", "")
                    items.add(item)

                } else if (curDay.readEd.toString() != myBook[i].edPage.toString()) {
                    val item = myBook[i]
                    item.curPage = curDay.readEd!!
                    myBook[i].lastDate = "${curDay.year}.${curDay.month}.${curDay.day}"
                    items.add(item)
                }
            }
            bookList.value = items
        }
    }

    fun setCategoryFinish() {
        viewModelScope.launch {
            val readingDays = db.readingDao().selectFinishRead()
            val items = ArrayList<MyBook>()
            for (i in readingDays) {
                val item = db.myBookDao().selectReadingBook(i.book)
                item.curPage = item.edPage
                item.lastDate = "${i.year}.${i.month}.${i.day}"
                items.add(item)
            }
            bookList.value = items
        }
    }

}





