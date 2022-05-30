package com.ilsamil.readingdiary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.db.entity.MyBook
import java.lang.Math.round

class BooksAdapter() : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {
    private var bItem : List<MyBook> = ArrayList<MyBook>()

    inner class BooksViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var nameTv : TextView = itemView.findViewById(R.id.item_mybooks_name_tv)
        var stTimeTv : TextView = itemView.findViewById(R.id.item_mybooks_st_time_tv)
        var progressBar : ProgressBar = itemView.findViewById(R.id.item_mybooks_progress_bar)
        var progressTv : TextView = itemView.findViewById(R.id.item_mybooks_progress_tv)
        var readingTv : TextView = itemView.findViewById(R.id.item_mybooks_reading_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mybooks, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val item : MyBook = bItem[position]
        holder.nameTv.text = item.name
        holder.stTimeTv.text = item.lastDate
        holder.progressBar.max = item.edPage.toInt()
        holder.progressBar.setProgress(item.curPage.toInt(), true)
        holder.progressTv.text = Math.floor((item.curPage.toDouble()/item.edPage.toDouble())*100).toInt().toString()+ "%"
        holder.readingTv.text = "${item.curPage}/${item.edPage} 페이지"
    }

    override fun getItemCount(): Int {
        return bItem.size
    }

    fun updateItems(items : List<MyBook>) {
        bItem = items
        notifyDataSetChanged()
    }
}