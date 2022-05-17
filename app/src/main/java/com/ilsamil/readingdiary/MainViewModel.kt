package com.ilsamil.readingdiary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val calLiveData = MutableLiveData<Int>()

}