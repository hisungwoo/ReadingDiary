package com.ilsamil.readingdiary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilsamil.readingdiary.model.ReadingDay

@Database(entities = [ReadingDay::class], version = 1)
abstract class ReadingDatabase : RoomDatabase() {
    abstract fun readingDao() : ReadingDao
}