package com.ilsamil.readingdiary.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.ilsamil.readingdiary.BR
import com.ilsamil.readingdiary.viewmodel.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import com.ilsamil.readingdiary.data.db.entity.CalendarDay
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
//        val readItem = ReadingDay("2022", "5", "26", "전지적 독자 시점", "0", "102", "555")
//        val readItem2 = ReadingDay("2022", "5", "26", "전지적 독자 시점", "102", "313", "555")
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
                if (!item.isEmpty && item.isRead) {
                    val calInfo = mainViewModel.getReadingDay(item.year, item.month, item.day)
                    Log.d("ttest", "calInfo = $calInfo")

                    AlertDialog.Builder(inflater.context)
                        .setView(R.layout.dialog_sel_calendar)
                        .show()
                        .also { alertDialog ->
                            if (alertDialog == null) return@also
                            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            val dateTitleTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_date_tv)
                            val bookNameTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_book_tv)
                            val progressBar = alertDialog.findViewById<ProgressBar>(R.id.dialog_cal_progress_bar)
                            val pageTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_page_tv)
                            val readingTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_reading_tv)

                            val editBtn = alertDialog.findViewById<Button>(R.id.dialog_cal_edit_btn)
                            val removeBtn = alertDialog.findViewById<Button>(R.id.dialog_cal_remove_btn)


                            dateTitleTv?.text = "${item.year}년 ${item.month}월 ${item.day}일"
                            bookNameTv?.text = "${calInfo.book}"
                            progressBar?.max = calInfo.maxPage?.toInt()!!
                            progressBar?.progress = calInfo.readEd?.toInt()!!
                            pageTv?.text = "${calInfo.readEd} / ${calInfo.maxPage}"

                            val readingPage = calInfo.readEd!!.toInt() - calInfo.readSt!!.toInt()
                            readingTv?.text = "$readingPage 장을 읽었습니다."


                            editBtn?.setOnClickListener {
                                alertDialog.dismiss()
                                val action =
                                    CalendarFragmentDirections.actionCalendarFragmentToAddReadingActivity(
                                        item
                                    )
                                findNavController().navigate(action)
                            }

                            removeBtn?.setOnClickListener {
                                val isComplete = mainViewModel.removeReadingDay(item.year, item.month, item.day)
                                if (isComplete >= 1) {
                                    readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
                                    mainViewModel.setCalendarList(selectedDate, readingList)
                                    alertDialog.dismiss()
                                }
                            }


                        }

                } else {
                    val action =
                        CalendarFragmentDirections.actionCalendarFragmentToAddReadingActivity(
                            item
                        )
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

    override fun onResume() {
        super.onResume()

        readingList = mainViewModel.getReadingDate(selectedDate.year.toString(), selectedDate.monthValue.toString())
        mainViewModel.setCalendarList(selectedDate, readingList)
    }
    

}


















