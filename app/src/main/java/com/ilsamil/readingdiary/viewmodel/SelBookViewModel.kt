package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelBookViewModel (application : Application) : AndroidViewModel(application)  {
    private val db = AppDatabase.getInstance(application.applicationContext)

    suspend fun getCurReading(name : String) : ReadingDay? {
        return withContext(viewModelScope.coroutineContext) {
            db!!.readingDao().selectMaxRead(name)
        }
    }

    suspend fun getStartReading(name : String) : ReadingDay? {
        return withContext(viewModelScope.coroutineContext) {
            db!!.readingDao().selectMinRead(name)
        }
    }

    suspend fun getReadingCnt(name : String) : String? {
        return withContext(viewModelScope.coroutineContext) {
            db!!.readingDao().selectReadCnt(name)
        }
    }

    fun removeBook(book : String)  {
        viewModelScope.launch {
            db!!.myBookDao().deleteBook(book)
            db!!.readingDao().deleteAllReadingDay(book)
        }
    }

}