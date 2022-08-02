package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.repository.KakaoBookRepository
import kotlinx.coroutines.launch

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