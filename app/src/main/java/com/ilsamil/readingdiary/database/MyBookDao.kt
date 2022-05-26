package com.ilsamil.readingdiary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilsamil.readingdiary.model.MyBook

@Dao
interface MyBookDao {
    @Query("SELECT * FROM MyBook")
    fun selectMyBook() : List<MyBook>

    @Insert
    fun insertBook(book : MyBook) : Long
}