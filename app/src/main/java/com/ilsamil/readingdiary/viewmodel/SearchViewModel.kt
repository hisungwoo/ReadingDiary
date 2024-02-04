package com.ilsamil.readingdiary.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.repository.KakaoBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: KakaoBookRepository) : ViewModel() {
    var searchItem = MutableLiveData<List<Books>>()

    fun getSearchBook(searchText : String) {
        viewModelScope.launch {
            val searchBooks = repository.getBookInfo(searchText)
            if (searchBooks != null) {
                searchItem.value = searchBooks.bookInfo
            }
        }
    }

    fun showSearchGuideTextView(searchItem: List<Books>): Boolean {
        return searchItem.isEmpty()
    }


}

@BindingAdapter("app:visiblee")
fun visiblee(view: View, visible: Boolean) {
    Log.d("wtest", "visible : $visible")
    view.visibility = if (visible) View.GONE else View.VISIBLE
}