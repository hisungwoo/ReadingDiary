package com.ilsamil.readingdiary.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ilsamil.readingdiary.model.ReadingDay

@Dao
interface ReadingDao {
    @Query("SELECT * FROM ReadingDay")
    fun selectReadingDay() : LiveData<List<ReadingDay>>

    @Query("SELECT DISTINCT day FROM ReadingDay WHERE year = :year AND month = :month ORDER BY day")
    fun selectReadingDate(year : String, month : String) : List<String>

    @Query("SELECT DISTINCT book FROM ReadingDay")
    fun selectBooks() : List<String>

    @Insert
    fun insertReadingDay(data : ReadingDay) : Long

    @Update
    fun updateReadingDay(data : ReadingDay)

    @Delete
    fun deleteReadingDay(data : ReadingDay)
}