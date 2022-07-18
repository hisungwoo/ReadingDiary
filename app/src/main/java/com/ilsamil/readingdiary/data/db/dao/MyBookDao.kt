package com.ilsamil.readingdiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ilsamil.readingdiary.data.db.entity.MyBook

@Dao
interface MyBookDao {
    @Query("SELECT * FROM MyBook")
    suspend fun selectMyBook() : List<MyBook>

    @Insert
    suspend fun insertBook(book : MyBook) : Long

    @Query("SELECT * FROM MyBook WHERE name = :name")
    suspend fun selectReadingBook(name: String) : MyBook?

    @Query("SELECT imgUrl FROM MyBook WHERE name = :name")
    suspend fun selectImgUrl(name: String) : String

    @Query("DELETE FROM MyBook WHERE name = :name")
    suspend fun deleteBook(name: String)

    @Query("SELECT count(*) FROM MyBook WHERE name = :name")
    suspend fun checkBook(name : String) : Int
}