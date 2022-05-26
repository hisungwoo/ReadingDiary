package com.ilsamil.readingdiary.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.ilsamil.readingdiary.BR
import com.ilsamil.readingdiary.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import com.ilsamil.readingdiary.model.CalendarDay
import com.ilsamil.readingdiary.model.MyBook
import com.ilsamil.readingdiary.model.ReadingDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var binding : CalendarListBinding
    private lateinit var selectedDate : LocalDate
    private lateinit var readingList : List<String>

    companion object {
        private const val TAG = "DEBUG_TAG_CalendarFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

//        val bookItem1 = MyBook("전지적 독자 시점", "www.naver.com", 0, 555, false)
//        val bookItem2 = MyBook("화산 귀환", "www.naver.com", 0, 1252, false)
//        mainViewModel.addBook(bookItem1)
//        mainViewModel.addBook(bookItem2)
//
//        val readItem = ReadingDay("2022", "5", "26", "전지적 독자 시점", "0", "102")
//        val readItem2 = ReadingDay("2022", "5", "26", "전지적 독자 시점", "102", "313")
//        mainViewModel.addReadingDiary(readItem)
//        mainViewModel.addReadingDiary(readItem2)


        //현재 날짜
        selectedDate = LocalDate.now()

        //독서 정보
        readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())

        Log.d(TAG, "read = $readingList")

        //년,월 텍스트 뷰 셋팅
        binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)

        val calendarAdapter = CalendarAdapter()
        binding.calRecyclerview.layoutManager = GridLayoutManager(context,
            7)
        binding.calRecyclerview.adapter = calendarAdapter
        mainViewModel.setCalendarList(selectedDate, readingList)

        mainViewModel.calReadList.observe(this, androidx.lifecycle.Observer {
            calendarAdapter.updateItem(it)
        })

        //클릭 이벤트
        calendarAdapter.setItemClickListener(object: CalendarAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, item : CalendarDay) {
//                val readItem = ReadingDay(item.year, item.month, item.day, true, "해리포터", 0, 500, 10)
//                addReading(readItem)
//
//                readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
//                mainViewModel.setCalendarList(selectedDate, readingList)

                if (!item.isEmpty && item.isRead) {
                    AlertDialog.Builder(inflater.context)
                        .setView(R.layout.dialog_sel_calendar)
                        .show()
                        .also { alertDialog ->
                            if (alertDialog == null) return@also
                            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            val editBtn = alertDialog.findViewById<Button>(R.id.dialog_cal_edit_btn)

                            editBtn?.setOnClickListener {
                                val action = CalendarFragmentDirections.actionCalendarFragmentToAddReadingActivity(item)
                                findNavController().navigate(action)
                            }


                        }

                } else {
                    val action = CalendarFragmentDirections.actionCalendarFragmentToAddReadingActivity(item)
                    findNavController().navigate(action)
                }
            }
        })

        binding.apply {
            setVariable(BR.model, ViewModelProvider(this@CalendarFragment).get(MainViewModel::class.java))
            lifecycleOwner = this@CalendarFragment
            model = mainViewModel
            executePendingBindings()

            calPrevBtn.setOnClickListener {
                selectedDate = selectedDate.minusMonths(1)
                readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
                mainViewModel.setCalendarList(selectedDate, readingList)
                binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)
            }

            calNextBtn.setOnClickListener {
                selectedDate = selectedDate.plusMonths(1)
                readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
                mainViewModel.setCalendarList(selectedDate, readingList)
                binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)
            }
        }

        return binding.root
    }


    // 날짜 타입 설정
    private fun monthYearFromDate(date : LocalDate) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        return date.format(formatter)
    }

    // 독서 기록 추가
    private fun addReading(readData : ReadingDay) {
        mainViewModel.addReadingDiary(readData)
    }

    override fun onResume() {
        super.onResume()

        readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
        mainViewModel.setCalendarList(selectedDate, readingList)
    }
    

}


















