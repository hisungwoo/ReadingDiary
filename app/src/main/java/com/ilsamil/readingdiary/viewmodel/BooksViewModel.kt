package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook

class BooksViewModel(application : Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "database-app")
        .allowMainThreadQueries()
        .build()

    fun getMyBooks() : List<MyBook> {
        return db.myBookDao().selectMyBook()
    }

    fun getCurPage(book : String) : String {
        return db.readingDao().selectMaxRead(book)
    }

}