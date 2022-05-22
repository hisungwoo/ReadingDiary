package com.ilsamil.readingdiary.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ilsamil.readingdiary.BR
import com.ilsamil.readingdiary.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import java.lang.Exception
import java.security.Key
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    lateinit var binding : CalendarListBinding
    lateinit var calendarAdapter : CalendarAdapter

    lateinit var selectedDate : LocalDate

    //Category.newInstance()사용을 위해 생성
    companion object {
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
        private const val TAG = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding : CalendarListBinding = CalendarListBinding.inflate(inflater, container, false)
//        val binding = DataBindingUtil.inflate<FragmentHomeBinding>()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.setVariable(BR.model, ViewModelProvider(this).get(MainViewModel::class.java))
        binding.lifecycleOwner = this
        binding.model = mainViewModel

//        binding.model.initCalendarList()
        binding.executePendingBindings()

        val manager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
//        calendarAdapter = CalendarAdapter()



        //현재 날짜
        selectedDate = LocalDate.now()

        //달력 set
        setCalendar()


//        mainViewModel.mCalendarList.observe(this, androidx.lifecycle.Observer {
//            val view = binding.pagerClaendar
//        })



        binding.calPrevBtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setCalendar()
        }


        binding.calNextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setCalendar()
        }


        return binding?.root
    }

    //날짜 타입 설정
    private fun monthYearFromDate(date : LocalDate) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        return date.format(formatter)
    }

    //화면 설정
    private fun setMonthView() {
        //년,월 텍스트 뷰 셋팅
        binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)
    }

    private fun daysInMonthArray(date : LocalDate) : ArrayList<String> {
        var dayList = ArrayList<String>()
        val yearMonth = YearMonth.from(date)

        // 해당 월 마지막 일
        val lastDay = yearMonth.lengthOfMonth()

        // 해당 월 첫번째 날
        val firstDay = selectedDate.withDayOfMonth(1)

        // 첫번째 요일
        val dayOfWeek = firstDay.dayOfWeek.value

        for(i in 1 .. 42) {
            if(i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add("")
            } else {
                dayList.add((i-dayOfWeek).toString())
            }
        }

        Log.d("ttest", dayList.toString())

        var isEmptyCheck = false
        for(i in 0..6) {
            Log.d("ttest", "i = " + dayList[i])
            if(dayList[i] == "") {
                isEmptyCheck = true
            } else {
                isEmptyCheck = false
                break
            }
        }
        if(isEmptyCheck) {
            dayList.removeAt(0)
            dayList.removeAt(0)
            dayList.removeAt(0)
            dayList.removeAt(0)
            dayList.removeAt(0)
            dayList.removeAt(0)
            dayList.removeAt(0)
        }

        Log.d("ttest", dayList.toString())


        return dayList
    }

    private fun setCalendar() {
        //년,월 텍스트뷰 셋팅
        setMonthView()

        val dayList = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(dayList)
        binding.calRecyclerview.layoutManager = GridLayoutManager(context,
            7)
        binding.calRecyclerview.adapter = calendarAdapter


    }

}


















