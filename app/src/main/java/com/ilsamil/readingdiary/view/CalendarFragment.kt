package com.ilsamil.readingdiary.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.BR
import com.ilsamil.readingdiary.viewmodel.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import com.ilsamil.readingdiary.data.db.entity.CalendarDay
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import com.ilsamil.readingdiary.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor

class CalendarFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var binding : CalendarListBinding
    private lateinit var selectedDate : LocalDate

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

        //현재 날짜
        selectedDate = LocalDate.now()
        val calendarAdapter = CalendarAdapter()

        binding.apply {
            calCurrentDateTv.text = monthYearFromDate(selectedDate)
            calRecyclerview.layoutManager = GridLayoutManager(context,
                7)
            calRecyclerview.adapter = calendarAdapter
        }

        // 독서 정보
        mainViewModel.setCalendar(selectedDate)
        mainViewModel.calReadList.observe(this, androidx.lifecycle.Observer {
            calendarAdapter.updateItem(it)
        })

        //클릭 이벤트
        calendarAdapter.setItemClickListener(object: CalendarAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, item : CalendarDay) {

                // 독서 정보가 있는 경우
                if (!item.isEmpty && item.isRead) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val calInfo = mainViewModel.getCalInfo(item)
                        val imgUrl = mainViewModel.getImgUrl2(calInfo)
                        setDialog(inflater.context, calInfo, imgUrl, item)
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
                mainViewModel.setCalendar(selectedDate)
                binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)
            }

            calNextBtn.setOnClickListener {
                selectedDate = selectedDate.plusMonths(1)
                mainViewModel.setCalendar(selectedDate)
                binding.calCurrentDateTv.text = monthYearFromDate(selectedDate)
            }
        }
        return binding.root
    }


    private fun setDialog(context : Context, readingDay: ReadingDay, imgUrl : String, item : CalendarDay) {
        AlertDialog.Builder(context)
            .setView(R.layout.dialog_sel_calendar)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) return@also
                alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val dateTitleTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_date_tv)
                val bookNameTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_book_tv)
                val progressBar = alertDialog.findViewById<ProgressBar>(R.id.dialog_cal_progress_bar)
                val progressTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_progress_tv)
                val pageTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_page_tv)
                val readingTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_reading_tv)

                val editBtn = alertDialog.findViewById<Button>(R.id.dialog_cal_edit_btn)
                val removeBtn = alertDialog.findViewById<Button>(R.id.dialog_cal_remove_btn)
                val bookImg = alertDialog.findViewById<ImageView>(R.id.dialog_cal_img_iv)
                val cancelBtn = alertDialog.findViewById<ImageButton>(R.id.dialog_cal_cancel_btn)

                Glide.with(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.img_loading)
                    .error(R.drawable.img_not)
                    .override(470,530)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(bookImg!!)

                dateTitleTv?.text = "${readingDay.year}년 ${readingDay.month}월 ${readingDay.day}일"
                bookNameTv?.text = "${readingDay.book}"
                progressBar?.max = readingDay.maxPage?.toInt()!!
                progressBar?.progress = readingDay.readEd!!
                progressTv?.text = floor((readingDay.readEd!!.toDouble()/ readingDay.maxPage!!.toDouble())*100).toInt().toString()+ "%"
                pageTv?.text = "${readingDay.readEd} / ${readingDay.maxPage} 페이지"

                val readingPage = readingDay.readEd!!.toInt() - readingDay.readSt!!.toInt()
                readingTv?.text = "$readingPage 장을 읽었습니다."


                editBtn?.setOnClickListener {
                    alertDialog.dismiss()
                    val action =
                        CalendarFragmentDirections.actionCalendarFragmentToAddReadingActivity(
                            item
                        )
                    findNavController().navigate(action)
                }

                removeBtn?.setOnClickListener { view ->
                    val util = Util()
                    val removeBook : () -> Unit = {
                        mainViewModel.removeReadingDay(readingDay.year, readingDay.month, readingDay.day, selectedDate)
                        alertDialog.dismiss()
                    }
                    util.showDialog(context, removeBook,"정말로 삭제 하시겠습니까?", "삭제")
                }

                cancelBtn?.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
    }

    // 날짜 타입 설정
    private fun monthYearFromDate(date : LocalDate) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        return date.format(formatter)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.setCalendar(selectedDate)
    }
}


















