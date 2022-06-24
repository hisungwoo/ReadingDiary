package com.ilsamil.readingdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ilsamil.readingdiary.data.db.AppDatabase
import com.ilsamil.readingdiary.data.db.entity.MyBook
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,"database-app")
        .build()

    val statsReadCnt = MutableLiveData<Int>()
    val statsBookCnt = MutableLiveData<Int>()

    fun setReadCnt() {
        viewModelScope.launch {
            statsReadCnt.value = db.readingDao().selectReadingCount()
        }
    }

    fun setBookCnt() {
        viewModelScope.launch {
            statsBookCnt.value = db.readingDao().selectFinishRead().size
        }
    }

}