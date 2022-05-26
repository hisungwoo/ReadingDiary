package com.ilsamil.readingdiary.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ilsamil.readingdiary.model.ReadingDay

@Dao
interface ReadingDao {
    @Query("SELECT * FROM ReadingDay")
    fun selectReadingDay() : List<ReadingDay>

    @Query("SELECT DISTINCT day FROM ReadingDay WHERE year = :year AND month = :month ORDER BY day")
    fun selectReadingDate(year : String, month : String) : List<String>

    @Query("SELECT MAX(readEd) FROM ReadingDay WHERE book = :book")
    fun selectMaxRead(book : String) : String

    @Insert
    fun insertReadingDay(data : ReadingDay) : Long

    @Update
    fun updateReadingDay(data : ReadingDay)

    @Delete
    fun deleteReadingDay(data : ReadingDay)
}