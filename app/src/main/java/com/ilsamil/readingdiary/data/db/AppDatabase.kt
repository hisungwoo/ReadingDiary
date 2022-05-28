package com.ilsamil.readingdiary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilsamil.readingdiary.data.db.dao.MyBookDao
import com.ilsamil.readingdiary.data.db.dao.ReadingDao
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay

@Database(entities = [ReadingDay::class, MyBook::class],  version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readingDao() : ReadingDao
    abstract fun myBookDao() : MyBookDao
}

