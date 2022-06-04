package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import kotlinx.coroutines.launch

class SearchResultViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
            application,
            AppDatabase::class.java, "database-app")
        .build()

    fun addBooks(book : MyBook) {
        viewModelScope.launch {
            db.myBookDao().insertBook(book)
        }
    }

}