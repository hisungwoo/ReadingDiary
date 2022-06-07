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

class BooksAdapter() : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {
    private var bItem : List<MyBook> = ArrayList()
    private lateinit var booksItemClickListener : BooksItemClickListener

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
            booksItemClickListener.onClick(it, position, bItem[position])
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
            progressBar.max = myBook.edPage
            progressBar.setProgress(myBook.curPage, true)
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("setProgressText")
        @JvmStatic
        fun setProgressText(tv : TextView, myBook : MyBook) {
            tv.text = Math.floor((myBook.curPage.toDouble()/myBook.edPage.toDouble())*100).toInt().toString()+ "%"
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("setReadingText")
        @JvmStatic
        fun setReadingText(tv : TextView, myBook : MyBook) {
            tv.text = "${myBook.curPage}/${myBook.edPage} 페이지"
        }

    }


    interface BooksItemClickListener {
        fun onClick(v: View, position: Int, item : MyBook)
    }

    fun setBooksItemClickListener(booksItemClickListener : BooksItemClickListener) {
        this.booksItemClickListener = booksItemClickListener
    }


}