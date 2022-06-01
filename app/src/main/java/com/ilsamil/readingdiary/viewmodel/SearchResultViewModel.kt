package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook

class SearchResultViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "database-app")
        .allowMainThreadQueries()
        .build()


    fun addBooks(book : MyBook) : Long{
        return db.myBookDao().insertBook(book)
    }

}