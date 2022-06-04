package com.ilsamil.readingdiary.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilsamil.readingdiary.data.db.entity.MyBook

@Dao
interface MyBookDao {
    @Query("SELECT * FROM MyBook")
    fun selectMyBook() : List<MyBook>

    @Query("UPDATE MyBook SET lastDate = :lastDate, CurPage = :curPage WHERE name = :name")
    fun updateCurPage(lastDate : String, curPage : String, name: String)

    @Insert
    fun insertBook(book : MyBook) : Long

    @Query("SELECT * FROM MyBook WHERE name = :name")
    fun selectReadingBook(name: String) : MyBook

    @Query("SELECT imgUrl FROM MyBook WHERE name = :name")
    fun selectImgUrl(name: String) : String

}