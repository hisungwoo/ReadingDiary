package com.ilsamil.readingdiary.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilsamil.readingdiary.data.db.entity.MyBook

@Dao
interface MyBookDao {
    @Query("SELECT * FROM MyBook")
    fun selectMyBook() : List<MyBook>

    @Query("SELECT curPage FROM MyBook WHERE name = :name")
    fun selectCurPage(name : String) : String

    @Query("UPDATE MyBook SET lastDate = :lastDate, CurPage = :curPage WHERE name = :name")
    fun updateCurPage(lastDate : String, curPage : String, name: String)

    @Insert
    fun insertBook(book : MyBook) : Long


}