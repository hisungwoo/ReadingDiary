package com.ilsamil.readingdiary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilsamil.readingdiary.data.db.dao.MyBookDao
import com.ilsamil.readingdiary.data.db.dao.ReadingDao
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.data.db.entity.ReadingDay

@Database(entities = [ReadingDay::class, MyBook::class],  version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readingDao() : ReadingDao
    abstract fun myBookDao() : MyBookDao

    companion object {
        private var instance : AppDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "database-app"
                    ).build()
                }
            }
            return instance
        }
    }
}

