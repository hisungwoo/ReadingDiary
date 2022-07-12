package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class SearchResultViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application.applicationContext)

    fun addBooks(book : MyBook) {
        viewModelScope.launch {
            db!!.myBookDao().insertBook(book)
        }
    }

    suspend fun getPageCnt(url : String) : String {
        return withContext(Dispatchers.IO) {
            var result = "0"
            val doc = Jsoup.connect(url).get()
            val temEle = doc.select("#tabContent > div:nth-child(1) > div.info_section.info_intro > div.wrap_cont > dl:nth-child(5) > dd")

            val isEmpty = temEle.isEmpty()
            if(!isEmpty) result = temEle.get(0).text().split(" ")[0]

            result
        }
    }
}