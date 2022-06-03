package com.ilsamil.readingdiary.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.viewmodel.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ActivityAddReadingBinding
import com.ilsamil.readingdiary.data.db.entity.ReadingDay

class AddReadingActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
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
            val calInfo = mainViewModel.getReadingDay(year, month, day)
            binding.addReadingBookNameTitle.text = calInfo.book
            binding.addReadingPageTv.text = "${calInfo.readEd} / ${calInfo.maxPage} 페이지"
            binding.addReadingEditPageBtn.isEnabled = true
            binding.addReadingSaveBtn.visibility = View.INVISIBLE
            binding.addReadingEditBtn.visibility = View.VISIBLE

            selBook = calInfo.book
            maxPage = calInfo.maxPage
            readSt = calInfo.readSt

        }

        binding.addReadingSelbookBtn.setOnClickListener {
            val books = mainViewModel.getBooks()
            val items = arrayOfNulls<String>(books.size)
            for (i in books.indices) items[i] = books[i].name

            val builder = AlertDialog.Builder(this)
                .setTitle("읽은 책을 선택해주세요")
                .setItems(items) { dialog, which ->
                    selBook = items[which].toString()
                    val imgUrl = books[which].imgUrl

                    val reading = mainViewModel.getCurPage(selBook!!)
                    if (reading == null) {
                        readSt = "0"
                    } else {
                        readSt = reading.readEd.toString()
                    }

                    maxPage = books[which].edPage.toString()
                    binding.addReadingBookNameTitle.text = items[which]
                    binding.addReadingPageTv.text = "${readSt} / ${maxPage} 페이지"
                    binding.addReadingEditPageBtn.isEnabled = true
                    binding.addReadingSaveBtn.isEnabled = true

                    Glide.with(this)
                        .load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.addReadingBookIv)

                }
            builder.show()
        }

        binding.addReadingEditPageBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setView(R.layout.dialog_page_eidt)
                .show()
                .also { alertDialog ->
                    if (alertDialog == null) return@also

                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val saveBtn = alertDialog.findViewById<Button>(R.id.dialog_page_save_btn)
                    val cancelBtn = alertDialog.findViewById<Button>(R.id.dialog_page_cancel_btn)
                    val edPageTv = alertDialog.findViewById<TextView>(R.id.dialog_page_end_tv)
                    val pageEt = alertDialog.findViewById<EditText>(R.id.dialog_page_edit_et)

                    edPageTv?.text = "/ $maxPage 페이지"

                    saveBtn?.setOnClickListener {
                        readEd = pageEt?.text.toString().toInt()

                        if (readSt!!.toInt() >= readEd!!.toInt()) {
                            Toast.makeText(this, "전보다 많은 페이지 입력", Toast.LENGTH_SHORT).show()
                        } else if(readEd!!.toInt() > maxPage!!.toInt()) {
                            Toast.makeText(this, "마지막 페이지를 넘음", Toast.LENGTH_SHORT).show()
                        } else {
                            binding.addReadingPageTv.text = "$readEd / $maxPage 페이지"
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
                mainViewModel.addReadingDiary(readItem)

//                val lastDate = "${year}-${month}-${day}"
//                mainViewModel.setCurPage(lastDate, readEd.toString(), selBook.toString())

                finish()
            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addReadingEditBtn.setOnClickListener {
            if (selBook != null && readSt != null && readEd != null) {
//                val curPage = mainViewModel.getCurPage(selBook.toString()).toInt()
                val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd, maxPage)
                mainViewModel.updateReadingDay(readItem)

//                if (curPage < readEd!!.toInt()) {
//                    val lastDate = "${year}-${month}-${day}"
//                    mainViewModel.setCurPage(lastDate, readEd.toString(), selBook.toString())
//                }

                finish()
            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }


        setContentView(binding.root)
    }

}