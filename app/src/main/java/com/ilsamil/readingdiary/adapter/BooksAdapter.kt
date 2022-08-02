package com.ilsamil.readingdiary.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.databinding.ItemMybooksBinding
import java.lang.Math.round
import kotlin.math.floor

class BooksAdapter() : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {
    private var bItem : List<MyBook> = ArrayList()
    var bookOnClickItem : (book : MyBook) -> Unit = {}

    inner class BooksViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMybooksBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mybooks, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.binding.myBook = bItem[position]

        // 클릭 이벤트
        holder.binding.itemMybooksCl.setOnClickListener {
            bookOnClickItem(bItem[position])
        }
    }

    override fun getItemCount(): Int {
        return bItem.size
    }

    fun updateItems(items : List<MyBook>) {
        bItem = items
        notifyDataSetChanged()
    }

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