package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.models.Books
import com.ilsamil.readingdiary.repository.KakaoBookRepository
import kotlinx.coroutines.launch

class SearchViewModel(application : Application) : AndroidViewModel(application) {
    private val service = KakaoBookRepository.getInstance()
    var searchItem = MutableLiveData<List<Books>>()

    fun getSearchBook(SearchText : String) {
        viewModelScope.launch {
            val searchBooks = service?.getBookInfo(SearchText)
            if (searchBooks != null) {
                searchItem.value = searchBooks.bookInfo
            }
        }
    }
}