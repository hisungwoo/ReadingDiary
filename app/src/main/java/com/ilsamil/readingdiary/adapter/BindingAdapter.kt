package com.ilsamil.readingdiary.adapter

import android.annotation.SuppressLint
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ilsamil.readingdiary.data.db.entity.MyBook
import kotlin.math.floor

object BindingAdapter {

    object BooksBindingAdapter {
        @BindingAdapter("setProgress")
        @JvmStatic
        fun setProgress(progressBar : ProgressBar, myBook : MyBook) {
            if(myBook.curPage == 0) {
                progressBar.max = 10000
                progressBar.progress = 1
            } else {
                progressBar.max = myBook.edPage
                progressBar.progress = myBook.curPage
            }
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("setProgressText")
        @JvmStatic
        fun setProgressText(tv : TextView, myBook : MyBook) {
            tv.text = floor((myBook.curPage.toDouble()/myBook.edPage.toDouble())*100).toInt().toString()+ "%"
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("setReadingText")
        @JvmStatic
        fun setReadingText(tv : TextView, myBook : MyBook) {
            tv.text = "${myBook.curPage}/${myBook.edPage} 페이지"
        }
    }

}