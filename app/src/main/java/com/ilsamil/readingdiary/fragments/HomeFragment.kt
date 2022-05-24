package com.ilsamil.readingdiary.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.ilsamil.readingdiary.BR
import com.ilsamil.readingdiary.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import com.ilsamil.readingdiary.model.CalendarDay
import com.ilsamil.readingdiary.model.ReadingDay
import java.lang.Exception
import java.security.Key
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var binding : CalendarListBinding
    private lateinit var selectedDate : LocalDate
    private lateinit var readingList : List<String>

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //현재 날짜
        selectedDate = LocalDate.now()
        //독서 정보
        readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())

//        Log.d("ttest", "read = $readingList")
//        setCalendar(readingList)

        //년,월 텍스트 뷰 셋팅
        binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)

        val calendarAdapter = CalendarAdapter()
        binding.calRecyclerview.layoutManager = GridLayoutManager(context,
            7)
        binding.calRecyclerview.adapter = calendarAdapter
        mainViewModel.setCalendarList(selectedDate, readingList)

        mainViewModel.calReadList.observe(this, androidx.lifecycle.Observer {
            Log.d("ttest" , "옵저버!!")
            calendarAdapter.updateItem(it)
        })


        //클릭 이벤트
        calendarAdapter.setItemClickListener(object: CalendarAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int, item : CalendarDay) {
                Toast.makeText(view?.context, item.day + "일!! " + item.isRead , Toast.LENGTH_SHORT).show()
//                val dd = ReadingDay(false, "2022", "5", item.day.toString(), true, "sss", 0, 55)
//                addReading(dd)
            }
        })



        binding.apply {
            setVariable(BR.model, ViewModelProvider(this@HomeFragment).get(MainViewModel::class.java))
            lifecycleOwner = this@HomeFragment
            model = mainViewModel
            executePendingBindings()

            calPrevBtn.setOnClickListener {
                selectedDate = selectedDate.minusMonths(1)
                readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
                setCalendar(readingList)
            }

            calNextBtn.setOnClickListener {
                selectedDate = selectedDate.plusMonths(1)
                readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
                setCalendar(readingList)
            }
        }

        return binding.root
    }

    //Calendar 셋팅
    private fun setCalendar(readingList : List<String>) {
        //년,월 텍스트 뷰 셋팅
        binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)

//        val dayList = mainViewModel.daysInMonthArray(selectedDate, readingList)
//        val calendarAdapter = CalendarAdapter(dayList)
        val calendarAdapter = CalendarAdapter()

        binding.calRecyclerview.layoutManager = GridLayoutManager(context,
            7)
        binding.calRecyclerview.adapter = calendarAdapter

//        //클릭 이벤트
//        calendarAdapter.setItemClickListener(object: CalendarAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                val dd = ReadingDay(false, "2022", "5", dayList[position].day.toString(), true, "sss", 0, 55)
//                Toast.makeText(view?.context, dayList[position].day + "일!!" , Toast.LENGTH_SHORT).show()
//                addReading(dd)
//            }
//        })
    }

    // 날짜 타입 설정
    private fun monthYearFromDate(date : LocalDate) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        return date.format(formatter)
    }

    // 독서 기록 추가
    private fun addReading(readData : ReadingDay) {
        val result = mainViewModel.addReadingDiary(readData)
        Log.d("ttest", "result = $result")
    }
    

}


















