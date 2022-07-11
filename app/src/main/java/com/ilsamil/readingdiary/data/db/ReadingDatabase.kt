package com.ilsamil.readingdiary.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.ilsamil.readingdiary.data.db.dao.MyBookDao
import com.ilsamil.readingdiary.data.db.dao.ReadingDao
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay

@Database(entities = [ReadingDay::class, MyBook::class],  version = 1)
abstract class ReadingDatabase : RoomDatabase() {
    abstract fun readingDao() : ReadingDao
    abstract fun myBookDao() : MyBookDao

    companion object {
        private var instance : ReadingDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : ReadingDatabase? {
            if (instance == null) {
                synchronized(ReadingDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReadingDatabase::class.java, "reading-database"
                    ).build()
                }
            }
            return instance
        }
    }
}