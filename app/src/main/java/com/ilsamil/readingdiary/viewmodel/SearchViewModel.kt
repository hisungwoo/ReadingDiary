package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ilsamil.readingdiary.BookService
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.data.remote.model.SearchBookDto
import com.ilsamil.readingdiary.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel(application : Application) : AndroidViewModel(application) {

    var searchItem = MutableLiveData<List<Books>>()



    fun getSearchBook(SearchText : String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookService::class.java)


        bookService.getBookInfo(SearchText, "accuracy", 50, "title")
            .enqueue(object : Callback<SearchBookDto> {
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    Log.d("ttest", " 성공!! ")

                    if (response.isSuccessful.not()) {
                        return
                    }

                    response.body()?.let {
                        Log.d("ttest", "body 있음")
//                        it.bookInfo.forEach { books ->
//                            Log.d("ttest", books.toString())
//                        }
                        searchItem.value = it.bookInfo

                    }

                }
                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    Log.d("ttest",t.toString())

                }
            })
    }




}