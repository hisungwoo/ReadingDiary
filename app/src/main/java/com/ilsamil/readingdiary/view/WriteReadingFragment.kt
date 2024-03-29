package com.ilsamil.readingdiary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.common.BaseFragment
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import com.ilsamil.readingdiary.databinding.FragmentWriteReadingBinding
import com.ilsamil.readingdiary.common.Util
import com.ilsamil.readingdiary.viewmodel.WriteReadingViewModel

class WriteReadingFragment : BaseFragment() {

    private val addReadingViewModel : WriteReadingViewModel by viewModels()
    private lateinit var binding : FragmentWriteReadingBinding
    private val args by navArgs<WriteReadingFragmentArgs>()

    private lateinit var year : String
    private lateinit var month : String
    private lateinit var day : String

    private var selBook : String? = null
    private var maxPage : String? = null
    private var readSt : String? = null
    private var readEd : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteReadingBinding.inflate(inflater, container, false)

        year = args.calday.year
        month = args.calday.month
        day = args.calday.day
        binding.addReadingDateTv.text = "${year}년 ${month}월 ${day}일"

        // 수정하기
        if (!args.calday.isEmpty && args.calday.isRead) {
            addReadingViewModel.setEdit(year, month, day)
            setEditType()
        }

        // 수정하기 UI
        addReadingViewModel.editReadingDay.observe(this, Observer {
            selBook = it.book
            maxPage = it.maxPage
            readSt = it.readSt
            binding.addReadingBookTitleTv.text = it.book
            binding.addReadingCurPageTv.text = it.readSt
            binding.addReadingLastPageTv.text = it.maxPage
            binding.addReadingTodayReadEt.setText(it.readEd.toString())

            addReadingViewModel.setImg(it.book)
        })

        // 수정하기 Img
        addReadingViewModel.editImg.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_not)
                .override(580,630)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.addReadingBookSelIv)
        })

        binding.addReadingBookNullVw.setOnClickListener {
            addReadingViewModel.setSelectBook()
        }

        addReadingViewModel.selBooks.observe(this, Observer {
            if (it.isEmpty()) {
                val util = Util()
                val moveSearch : () -> Unit = {
                    findNavController().navigate(R.id.action_writeReadingFragment_to_searchFragment)
                }
                util.showDialog(inflater.context, moveSearch,"등록된 책이 없습니다 책을 추가해주세요", "이동")
            } else {
                val items = Array(it.size) { "null" }
                for (i in items.indices) items[i] = it[i].name

                val builder = AlertDialog.Builder(inflater.context)
                    .setTitle("책 선택")
                    .setItems(items) { dialog, which ->
                        selBook = it[which].name
                        readSt = it[which].curPage.toString()
                        maxPage = it[which].edPage.toString()

                        binding.addReadingBookTitleTv.text = it[which].name
                        binding.addReadingCurPageTv.text = readSt
                        binding.addReadingLastPageTv.text = maxPage
                        setSelBook()

                        val imgUrl = it[which].imgUrl
                        Glide.with(this)
                            .load(imgUrl)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.img_loading)
                            .error(R.drawable.img_not)
                            .override(580,630)
                            .into(binding.addReadingBookSelIv)

                        dialog.dismiss()
                    }
                builder.show()
            }
        })

        binding.addReadingCancelBtn.setOnClickListener {
            cancelBook()
        }

        binding.addReadingSaveBtn.setOnClickListener {
            if(selBook != null) {
                val inputReading : Int? = when(binding.addReadingTodayReadEt.text.isNotEmpty()) {
                    true -> binding.addReadingTodayReadEt.text.toString().toInt()
                    else -> null
                }

                if(inputReading == null) {
                    Toast.makeText(inflater.context, "오늘 독서한 페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if (readSt!!.toInt() >= inputReading!!) {
                    Toast.makeText(inflater.context, "이전보다 많은 페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if(inputReading > maxPage!!.toInt()) {
                    Toast.makeText(inflater.context, "페이지 총수보다 적게 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    readEd = inputReading
                    if (selBook != null && readSt != null && readEd != null) {
                        val util = Util()
                        val addReadingDay : () -> Unit = {
                            val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                            addReadingViewModel.addReadingDiary(readItem)
                            findNavController().popBackStack()
                        }
                        util.showDialog(inflater.context, addReadingDay,"오늘 독서기록을 저장하시겠어요?", "저장")
                    }
                }
            } else {
                Toast.makeText(inflater.context, "오늘 읽은 책을 선택해주세요", Toast.LENGTH_SHORT).show()
            }

        }

        binding.addReadingEditBtn.setOnClickListener {
            if(selBook != null) {
                val inputReading : Int? = when(binding.addReadingTodayReadEt.text.isNotEmpty()) {
                    true -> binding.addReadingTodayReadEt.text.toString().toInt()
                    else -> null
                }

                if(inputReading == null) {
                    Toast.makeText(inflater.context, "오늘 독서한 페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if (readSt!!.toInt() >= inputReading!!) {
                    Toast.makeText(inflater.context, "이전보다 많은 페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if(inputReading > maxPage!!.toInt()) {
                    Toast.makeText(inflater.context, "페이지 총수보다 적게 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    readEd = inputReading
                    if (readSt != null && readEd != null) {
                        val util = Util()
                        val updateReadingDay : () -> Unit = {
                            val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                            addReadingViewModel.updateReadingDay(readItem)
                            findNavController().popBackStack()
                        }
                        util.showDialog(inflater.context, updateReadingDay,"오늘 독서기록을 수정하시겠어요?", "수정")
                    }
                }
            } else {
                Toast.makeText(inflater.context, "오늘 읽은 책을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setEditType() {
        binding.apply {
            addReadingSaveBtn.visibility = View.INVISIBLE
            addReadingEditBtn.visibility = View.VISIBLE
        }
        setSelBook()
    }

    private fun setSelBook() {
        binding.apply {
            addReadingSaveBtn.visibility = View.VISIBLE
            addReadingTodayReadEt.isFocusable = true
            addReadingBookNullIv.visibility = View.INVISIBLE
            addReadingBookNullTv.visibility = View.INVISIBLE
            addReadingBookNullVw.visibility = View.INVISIBLE
            addReadingBookSelIv.visibility = View.VISIBLE
            addReadingBookTitleTv.visibility = View.VISIBLE
            addReadingCancelBtn.visibility = View.VISIBLE
        }
    }

    private fun cancelBook() {
        selBook = null
        readSt = null
        maxPage = null

        binding.apply {
            addReadingBookTitleTv.text = ""
            addReadingCurPageTv.text = "-"
            addReadingLastPageTv.text = "-"
            addReadingSaveBtn.visibility = View.INVISIBLE
            addReadingBookNullIv.visibility = View.VISIBLE
            addReadingBookNullTv.visibility = View.VISIBLE
            addReadingBookNullVw.visibility = View.VISIBLE
            addReadingBookSelIv.visibility = View.INVISIBLE
            addReadingBookTitleTv.visibility = View.INVISIBLE
            addReadingCancelBtn.visibility = View.INVISIBLE
        }
    }

}