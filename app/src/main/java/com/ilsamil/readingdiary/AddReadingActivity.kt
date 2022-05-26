package com.ilsamil.readingdiary

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.navArgs
import com.ilsamil.readingdiary.databinding.ActivityAddReadingBinding
import com.ilsamil.readingdiary.model.MyBook
import com.ilsamil.readingdiary.model.ReadingDay
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalDateTime

class AddReadingActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding : ActivityAddReadingBinding
    private val args by navArgs<AddReadingActivityArgs>()

    private lateinit var year : String
    private lateinit var month : String
    private lateinit var day : String
    private var selBook : String? = null
    private var minPage : String? = null
    private var maxPage : String? = null

    private var readSt : String? = null
    private var readEd : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReadingBinding.inflate(layoutInflater)

        year = args.calday.year
        month = args.calday.month
        day = args.calday.day

        binding.addReadingDateTv.text = "${year}년 ${month}월 ${day}일"

        binding.addReadingSelbookBtn.setOnClickListener {
            val books = mainViewModel.getBooks()
            val items = arrayOfNulls<String>(books.size)
            for (i in books.indices) items[i] = books[i].name

            val builder = AlertDialog.Builder(this)
                .setTitle("읽은 책을 선택해주세요")
                .setItems(items) { dialog, which ->
                    selBook = items[which].toString()
                    readSt = mainViewModel.getEdPage(selBook!!)
                    if(readSt == null) readSt = "0"

                    minPage = books[which].stPage.toString()
                    maxPage = books[which].edPage.toString()

                    binding.addReadingBookNameTitle.text = items[which]
                    binding.addReadingPageTv.text = "${readSt} / ${maxPage} 페이지"
                    binding.addReadingEditBtn.isEnabled = true
                    binding.addReadingSaveBtn.isEnabled = true
                }
            builder.show()
        }

        binding.addReadingEditBtn.setOnClickListener {
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
                        readEd = pageEt?.text.toString()

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
                val readItem = ReadingDay(year, month, day, selBook!!, readSt, readEd)
                mainViewModel.addReadingDiary(readItem)
                finish()
            } else {
                Toast.makeText(this, "페이지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }


        setContentView(binding.root)
    }

}