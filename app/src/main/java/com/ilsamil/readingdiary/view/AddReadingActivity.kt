package com.ilsamil.readingdiary.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.viewmodel.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ActivityAddReadingBinding
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
import com.ilsamil.readingdiary.utils.Util
import com.ilsamil.readingdiary.viewmodel.AddReadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddReadingActivity : AppCompatActivity() {
    private val addReadingViewModel : AddReadingViewModel by viewModels()
    private lateinit var binding : ActivityAddReadingBinding
    private val args by navArgs<AddReadingActivityArgs>()

    private lateinit var year : String
    private lateinit var month : String
    private lateinit var day : String

    private var selBook : String? = null
    private var maxPage : String? = null
    private var readSt : String? = null
    private var readEd : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReadingBinding.inflate(layoutInflater)

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
            binding.addReadingUpdatePageBtn.text = "오늘 ${it.readEd.toString()}페이지까지 읽었습니다"

            addReadingViewModel.setImg(it.book)
        })

        // 수정하기 Img
        addReadingViewModel.editImg.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.addReadingBookSelIv)
        })

        binding.addReadingBookNullVw.setOnClickListener {
            addReadingViewModel.setSelectBook()
        }

        addReadingViewModel.selBooks.observe(this, Observer {
            val items = Array(it.size) { "null" }
            for (i in items.indices) items[i] = it[i].name

            val builder = AlertDialog.Builder(this)
                .setTitle("읽은 책을 선택해주세요")
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
                        .into(binding.addReadingBookSelIv)

                }
            builder.show()
        })

        binding.addReadingUpdatePageBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setView(R.layout.dialog_page_update)
                .show()
                .also { alertDialog ->
                    if (alertDialog == null) return@also
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val saveBtn = alertDialog.findViewById<Button>(R.id.dialog_update_save_btn)
                    val cancelBtn = alertDialog.findViewById<Button>(R.id.dialog_update_cancel_btn)
                    val edPageTv = alertDialog.findViewById<TextView>(R.id.dialog_update_end_tv)
                    val pageEt = alertDialog.findViewById<EditText>(R.id.dialog_update_edit_et)
                    edPageTv?.text = "/ $maxPage 페이지"

                    saveBtn?.setOnClickListener {
                        if (pageEt?.text.toString() != "") {
                            val isInput = pageEt?.text.toString().toInt()
                            if (readSt!!.toInt() >= isInput) {
                                Toast.makeText(this, "이전보다 많은 페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
                            } else if(isInput > maxPage!!.toInt()) {
                                Toast.makeText(this, "페이지 총수보다 적게 입력해주세요", Toast.LENGTH_SHORT).show()
                            } else {
                                binding.addReadingUpdatePageBtn.text = "오늘 ${isInput}페이지까지 읽었습니다"
                                readEd = isInput
                                alertDialog.dismiss()
                            }
                        }
                    }

                    cancelBtn?.setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
        }

        binding.addReadingCancelBtn.setOnClickListener {
            cancelBook()
        }

        binding.addReadingSaveBtn.setOnClickListener {
            if (selBook != null && readSt != null && readEd != null) {
                val util = Util()
                val addReadingDay : () -> Unit = {
                    val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                    addReadingViewModel.addReadingDiary(readItem)
                    finish()
                }
                util.showDialog(this, addReadingDay,"오늘 독서기록을 저장하시겠어요?", "저장")

            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addReadingEditBtn.setOnClickListener {
            if (selBook != null && readSt != null && readEd != null) {
                val util = Util()
                val updateReadingDay : () -> Unit = {
                    val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                    addReadingViewModel.updateReadingDay(readItem)
                    finish()
                }
                util.showDialog(this, updateReadingDay,"오늘 독서기록을 수정하시겠어요?", "수정")

            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
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
            addReadingUpdatePageBtn.isEnabled = true
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
            addReadingUpdatePageBtn.isEnabled = false
            addReadingUpdatePageBtn.text = "오늘 읽은 페이지 입력"

            addReadingBookNullIv.visibility = View.VISIBLE
            addReadingBookNullTv.visibility = View.VISIBLE
            addReadingBookNullVw.visibility = View.VISIBLE
            addReadingBookSelIv.visibility = View.INVISIBLE
            addReadingBookTitleTv.visibility = View.INVISIBLE
            addReadingCancelBtn.visibility = View.INVISIBLE
        }
    }

}