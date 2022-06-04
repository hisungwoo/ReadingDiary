package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay

class BooksViewModel(application : Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "database-app")
        .allowMainThreadQueries()
        .build()

    val bookList = MutableLiveData<List<MyBook>>()

    private fun getMyBooks() : List<MyBook> {
        return db.myBookDao().selectMyBook()
    }

    private fun getCurDay(book : String) : ReadingDay {
        return db.readingDao().selectMaxRead(book)
    }

    private fun getFinishReadingDay() : List<ReadingDay> {
        return db.readingDao().selectFinishRead()
    }

    private fun getReadingBook(name : String) : MyBook {
        return db.myBookDao().selectReadingBook(name)
    }

    fun setCategoryAll() {
        val items = getMyBooks()
        for (i in items.indices) {
            val curDay = getCurDay(items[i].name)
            if (curDay == null) items[i].curPage = 0
            else {
                items[i].curPage = curDay.readEd!!
                items[i].lastDate = "${curDay.year}.${curDay.month}.${curDay.day}"
            }
        }
        bookList.value = items
    }

    fun setCategoryReading() {
        val myBook = getMyBooks()
        val items = ArrayList<MyBook>()
        for (i in myBook.indices) {
            val curDay : ReadingDay? = getCurDay(myBook[i].name)
            if (curDay == null) {
                val item = MyBook(myBook[i].name, myBook[i].imgUrl, "", myBook[i].edPage, 0)
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

    fun setCategoryFinish() {
        val readingDays = getFinishReadingDay()
        val items = ArrayList<MyBook>()
        for (i in readingDays) {
            val item = getReadingBook(i.book)
            item.curPage = item.edPage
            item.lastDate = "${i.year}.${i.month}.${i.day}"
            items.add(item)
        }
        bookList.value = items
    }

}





