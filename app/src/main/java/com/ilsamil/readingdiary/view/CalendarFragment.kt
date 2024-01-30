package com.ilsamil.readingdiary.view

import android.content.Context
import android.os.Bundle
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
import com.ilsamil.readingdiary.viewmodel.CalendarViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import com.ilsamil.readingdiary.data.db.entity.CalendarDay
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import com.ilsamil.readingdiary.utils.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.floor

class CalendarFragment : BaseFragment() {
    private val mainViewModel by activityViewModels<CalendarViewModel>()
    private lateinit var binding: CalendarListBinding
    private lateinit var selectedDate: LocalDate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        //현재 날짜
        selectedDate = LocalDate.now()
        val calendarAdapter = CalendarAdapter().apply { calOnClickItem = this@CalendarFragment::moveWriteReadingItem }

        binding.apply {
            calCurrentDateTv.text = monthYearFromDate(selectedDate)
            calRecyclerview.layoutManager = GridLayoutManager(
                context,
                7
            )
            calRecyclerview.adapter = calendarAdapter
        }

        // 독서 달력 셋팅
        mainViewModel.apply {
            setCalendar(selectedDate)
            calReadList.observe(viewLifecycleOwner) {
                calendarAdapter.updateItem(it)
            }
        }

        binding.apply {
            setVariable(BR.model, ViewModelProvider(this@CalendarFragment)[CalendarViewModel::class.java])
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

    // 달력 클릭
    private fun moveWriteReadingItem(calDay: CalendarDay) {
        if (!calDay.isEmpty && calDay.isRead) {
            // 독서 정보가 있는 경우
            CoroutineScope(Dispatchers.Main).launch {
                val calInfo = mainViewModel.getCalInfo(calDay)
                val imgUrl = mainViewModel.getImgUrl(calInfo)
                setDialog(requireContext(), calInfo, imgUrl, calDay)
            }


        } else {
            // 독서 정보가 없는 경우 : 독서날 생성 페이지로 이동
            val action = CalendarFragmentDirections.actionCalendarFragmentToWriteReadingFragment(calDay)
            findNavController().navigate(action)
        }
    }


    private fun setDialog(context: Context, readingDay: ReadingDay, imgUrl: String, item: CalendarDay) {
        AlertDialog.Builder(context)
            .setView(R.layout.dialog_sel_calendar)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) return@also

                val dateTitleTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_date_tv)
                val bookNameTv = alertDialog.findViewById<TextView>(R.id.dialog_cal_book_tv)
                val progressBar =
                    alertDialog.findViewById<ProgressBar>(R.id.dialog_cal_progress_bar)
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
                    .override(470, 530)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(bookImg!!)


                dateTitleTv?.text = "${readingDay.year}년 ${readingDay.month}월 ${readingDay.day}일"
                bookNameTv?.text = "${readingDay.book}"
                progressBar?.max = readingDay.maxPage?.toInt()!!
                progressBar?.progress = readingDay.readEd!!
                progressTv?.text =
                    floor((readingDay.readEd!!.toDouble() / readingDay.maxPage!!.toDouble()) * 100).toInt()
                        .toString() + "%"
                pageTv?.text = "${readingDay.readEd} / ${readingDay.maxPage} 페이지"

                val readingPage = readingDay.readEd!!.toInt() - readingDay.readSt!!.toInt()
                readingTv?.text = "$readingPage 장을 읽었습니다."


                editBtn?.setOnClickListener {
                    alertDialog.dismiss()
                    val action =
                        CalendarFragmentDirections.actionCalendarFragmentToWriteReadingFragment(item)
                    findNavController().navigate(action)
                }

                removeBtn?.setOnClickListener { _ ->
                    val util = Util()
                    val removeBook: () -> Unit = {
                        mainViewModel.removeReadingDay(
                            readingDay.year,
                            readingDay.month,
                            readingDay.day,
                            selectedDate
                        )
                        alertDialog.dismiss()
                    }
                    util.showDialog(context, removeBook, "정말로 삭제 하시겠습니까?", "삭제")
                }

                cancelBtn?.setOnClickListener {
                    alertDialog.dismiss()
                }

            }
    }

    // 날짜 타입 설정
    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        return date.format(formatter)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.setCalendar(selectedDate)
    }

}


















