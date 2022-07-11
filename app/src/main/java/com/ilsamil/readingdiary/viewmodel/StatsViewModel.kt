package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.ReadingDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = ReadingDatabase.getInstance(application.applicationContext)

    val statsReadCnt = MutableLiveData<Int>()
    val statsBookCnt = MutableLiveData<Int>()
    val finishBook = MutableLiveData<List<String>>()

    fun setReadCnt() {
        viewModelScope.launch {
            statsReadCnt.value = db!!.readingDao().selectReadingCount()
        }
    }

    fun setBookCnt() {
        viewModelScope.launch {
            statsBookCnt.value = db!!.readingDao().selectFinishRead().size
        }
    }

    fun getFinishBook() {
        viewModelScope.launch {
            val readingDays = db!!.readingDao().selectFinishRead()
            val items = ArrayList<String>()
            for (i in readingDays) {
                val item = db.myBookDao().selectReadingBook(i.book)
                if (item != null) {
                    items.add(item.name)
                }
            }

            finishBook.value = items
        }
    }

}