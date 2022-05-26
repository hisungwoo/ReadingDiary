package com.ilsamil.readingdiary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilsamil.readingdiary.model.MyBook
import com.ilsamil.readingdiary.model.ReadingDay

@Database(entities = [ReadingDay::class, MyBook::class],  version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readingDao() : ReadingDao
    abstract fun myBookDao() : MyBookDao
}

