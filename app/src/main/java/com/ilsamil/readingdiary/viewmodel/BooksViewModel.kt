package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.launch

class BooksViewModel(application : Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application.applicationContext)

    val bookAllList = MutableLiveData<List<MyBook>>()
    val bookReadingList = MutableLiveData<List<MyBook>>()
    val bookFinishList = MutableLiveData<List<MyBook>>()

    fun setCategoryAll() {
        viewModelScope.launch {
            val items = db!!.myBookDao().selectMyBook()
            for (i in items.indices) {
                val curDay : ReadingDay? = db!!.readingDao().selectMaxRead(items[i].name)
                if (curDay == null) {
                    items[i].curPage = 0
                    items[i].lastDate = "- 독서를 시작해보세요"
                }
                else {
                    items[i].curPage = curDay.readEd!!
                    items[i].lastDate = "${curDay.year}.${curDay.month}.${curDay.day}"
                }
            }

            // 날짜 내림차순 정렬
            val resultItems = items.sortedByDescending { it.lastDate }
            bookAllList.value = resultItems
        }
    }

    fun setCategoryReading() {
        viewModelScope.launch {
            val myBook = db!!.myBookDao().selectMyBook()
            val items = ArrayList<MyBook>()
            for (i in myBook.indices) {
                val curDay : ReadingDay? = db.readingDao().selectMaxRead(myBook[i].name)
                if (curDay == null) {
                    val item = myBook[i]
                    item.curPage = 0
                    item.lastDate = "- 독서를 시작해보세요"
                    items.add(item)
                } else if (curDay.readEd.toString() != myBook[i].edPage.toString()) {
                    val item = myBook[i]
                    item.curPage = curDay.readEd!!
                    myBook[i].lastDate = "${curDay.year}.${curDay.month}.${curDay.day}"
                    items.add(item)
                }
            }
            // 날짜 내림차순 정렬
            val resultItems = items.sortedByDescending { it.lastDate }
            bookReadingList.value = resultItems
        }
    }

    fun setCategoryFinish() {
        viewModelScope.launch {
            val readingDays = db!!.readingDao().selectFinishRead()
            val items = ArrayList<MyBook>()
            for (i in readingDays) {
                val item = db.myBookDao().selectReadingBook(i.book)
                if (item != null) {
                    item.curPage = item.edPage
                    item.lastDate = "${i.year}.${i.month}.${i.day}"
                    items.add(item)
                }
            }
            // 날짜 내림차순 정렬
            val resultItems = items.sortedByDescending { it.lastDate }
            bookFinishList.value = resultItems
        }
    }

}





