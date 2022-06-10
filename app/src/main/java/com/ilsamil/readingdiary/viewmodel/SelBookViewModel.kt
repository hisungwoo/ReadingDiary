package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelBookViewModel (application : Application) : AndroidViewModel(application)  {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,"database-app")
        .build()


    suspend fun getCurReading(name : String) : ReadingDay? {
        return withContext(viewModelScope.coroutineContext) {
            db.readingDao().selectMaxRead(name)
        }
    }

    suspend fun getStartReading(name : String) : ReadingDay? {
        return withContext(viewModelScope.coroutineContext) {
            db.readingDao().selectMinRead(name)
        }
    }

    fun removeBook(book : MyBook) {
        viewModelScope.launch {
            db.myBookDao().deleteBook(book)
        }
    }

    fun removeReading(book : String) {
        viewModelScope.launch {
            db.readingDao().deleteAllReadingDay(book)
        }
    }
}