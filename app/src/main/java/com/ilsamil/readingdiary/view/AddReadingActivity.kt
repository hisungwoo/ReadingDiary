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
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.viewmodel.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ActivityAddReadingBinding
import com.ilsamil.readingdiary.data.db.entity.ReadingDay
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
    private var isAdd = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReadingBinding.inflate(layoutInflater)

        year = args.calday.year
        month = args.calday.month
        day = args.calday.day

        binding.addReadingDateTv.text = "${year}년 ${month}월 ${day}일"

        //수정하기
        if (!args.calday.isEmpty && args.calday.isRead) {
            isAdd = false
            addReadingViewModel.setEdit(year, month, day)
            binding.addReadingUpdatePageBtn.isEnabled = true
            binding.addReadingSaveBtn.visibility = View.INVISIBLE
            binding.addReadingEditBtn.visibility = View.VISIBLE
        }

//        addReadingViewModel.editReadingDay.observe(this, Observer {
//            binding.addReadingBookTitleTv.text = it.book
//            binding.addReadingCurPageTv.text = it.readEd.toString()
//            binding.addReadingLastPageTv.text = it.maxPage
//            selBook = it.book
//            maxPage = it.maxPage
//            readSt = it.readSt
//            addReadingViewModel.setEditImg(it.book)
//        })

        addReadingViewModel.editImg.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(binding.addReadingBookIv)
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
                    val imgUrl = it[which].imgUrl
                    readSt = it[which].curPage.toString()

                    maxPage = it[which].edPage.toString()
                    binding.addReadingBookTitleTv.text = it[which].name
                    binding.addReadingCurPageTv.text = readSt
                    binding.addReadingLastPageTv.text = maxPage

                    binding.addReadingUpdatePageBtn.isEnabled = true
                    binding.addReadingSaveBtn.isEnabled = true

                    binding.addReadingBookNullIv.visibility = View.INVISIBLE
                    binding.addReadingBookNullTv.visibility = View.INVISIBLE
                    binding.addReadingBookNullVw.visibility = View.INVISIBLE
                    binding.addReadingBookSelIv.visibility = View.VISIBLE
                    binding.addReadingBookTitleTv.visibility = View.VISIBLE

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
                        val isInput = pageEt?.text.toString().toInt()

                        if (readSt!!.toInt() >= isInput) {
                            Toast.makeText(this, "전보다 많은 페이지 입력", Toast.LENGTH_SHORT).show()
                        } else if(isInput > maxPage!!.toInt()) {
                            Toast.makeText(this, "마지막 페이지를 넘음", Toast.LENGTH_SHORT).show()
                        } else {
//                            binding.addReadingPageTv.text = "$readEd / $maxPage 페이지"
                            binding.addReadingUpdatePageBtn.text = "${isInput} 페이지를 읽었습니다"
                            readEd = isInput
                            alertDialog.dismiss()
                        }
                    }

                    cancelBtn?.setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
        }


        binding.addReadingSaveBtn.setOnClickListener {
            if (selBook != null && readSt != null && readEd != null) {
                val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                addReadingViewModel.addReadingDiary(readItem)
                finish()
            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addReadingEditBtn.setOnClickListener {
            if (selBook != null && readSt != null && readEd != null) {
                val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                addReadingViewModel.updateReadingDay(readItem)
                finish()
            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }


        setContentView(binding.root)
    }

}