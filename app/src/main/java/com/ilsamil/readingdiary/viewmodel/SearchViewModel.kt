package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.remote.api.BookService
import com.ilsamil.readingdiary.data.remote.model.Books
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel(application : Application) : AndroidViewModel(application) {
    var searchItem = MutableLiveData<List<Books>>()
    private val service : BookService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BookService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(BookService::class.java)
    }

    fun getSearchBook(SearchText : String) {
        viewModelScope.launch {
            val searchBooks = service.getBookInfo(SearchText, "accuracy", 50, "title")
            searchItem.value = searchBooks.bookInfo
        }
    }
}