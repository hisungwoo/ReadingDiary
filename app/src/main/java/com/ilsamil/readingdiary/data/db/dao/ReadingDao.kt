package com.ilsamil.readingdiary.data.db.dao

import androidx.room.*
import com.ilsamil.readingdiary.data.db.entity.ReadingDay

@Dao
interface ReadingDao {
    @Query("SELECT * FROM ReadingDay WHERE year = :year AND month = :month AND day = :day")
    fun selectReadingDay(year : String, month : String, day : String) : ReadingDay

    @Query("SELECT DISTINCT day FROM ReadingDay WHERE year = :year AND month = :month ORDER BY day")
    fun selectReadingDate(year : String, month : String) : List<String>

    @Query("SELECT MAX(readEd) FROM ReadingDay WHERE book = :book")
    fun selectMaxRead(book : String) : String

    @Query("DELETE FROM ReadingDay WHERE year = :year AND month = :month AND day = :day")
    fun deleteReadingDay(year : String, month : String, day : String) : Int

    @Insert
    fun insertReadingDay(data : ReadingDay) : Long

    @Query("UPDATE ReadingDay SET book = :book, readSt = :readSt, readEd = :readEd, maxPage = :maxPage WHERE year = :year and month = :month and day = :day")
    fun updateReadingDay(year : String, month : String, day : String, book: String, readSt : String, readEd : String, maxPage : String)
}