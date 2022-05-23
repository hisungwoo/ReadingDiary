package com.ilsamil.readingdiary.database

import androidx.room.*
import com.ilsamil.readingdiary.model.ReadingDay

@Dao
interface ReadingDao {
    @Query("SELECT * FROM ReadingDay WHERE year = :year AND month = :month")
    fun selectReadingDay(year : String, month : String, ) : List<ReadingDay>

    @Query("SELECT DISTINCT day FROM ReadingDay WHERE year = :year AND month = :month")
    fun selectReadingDate(year : String, month : String, ) : List<String>

    @Insert
    fun insertReadingDay(data : ReadingDay)

    @Update
    fun updateReadingDay(data : ReadingDay)

    @Delete
    fun deleteReadingDay(data : ReadingDay)
}