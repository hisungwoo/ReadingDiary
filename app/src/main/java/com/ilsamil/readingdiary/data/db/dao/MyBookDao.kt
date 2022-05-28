package com.ilsamil.readingdiary.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilsamil.readingdiary.data.db.entity.MyBook

@Dao
interface MyBookDao {
    @Query("SELECT * FROM MyBook")
    fun selectMyBook() : List<MyBook>

    @Insert
    fun insertBook(book : MyBook) : Long
}