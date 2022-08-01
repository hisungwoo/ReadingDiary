package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.remote.api.BookInterface
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.repositorty.KakaoBookRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel(application : Application) : AndroidViewModel(application) {
    var searchItem = MutableLiveData<List<Books>>()
    var service = KakaoBookRepository()

    fun getSearchBook(SearchText : String) {
        viewModelScope.launch {
            val searchBooks = service.getBookInfo(SearchText)
            searchItem.value = searchBooks.bookInfo
        }
    }
}