package com.ilsamil.readingdiary

import android.content.Context
import android.icu.util.LocaleData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.ilsamil.readingdiary.databinding.ActivityAddReadingBinding
import com.ilsamil.readingdiary.model.ReadingDay
import java.time.LocalDate
import java.time.LocalDateTime

class AddReadingActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding : ActivityAddReadingBinding
    private val args by navArgs<AddReadingActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReadingBinding.inflate(layoutInflater)
        val dd = mainViewModel.getBooks()

        Log.d("ttest", "boks = $dd")

        val year = args.calday.year
        val month = args.calday.month
        val day = args.calday.day

        binding.addReadingDateTv.text = "$year 년 $month 월 $day 일"




        binding.addReadingSaveBtn.setOnClickListener {
            val readItem = ReadingDay(year, month, day, true, "어벤져스", 0, 500, 10)
            mainViewModel.addReadingDiary(readItem)
            finish()
        }


        setContentView(binding.root)
    }

}