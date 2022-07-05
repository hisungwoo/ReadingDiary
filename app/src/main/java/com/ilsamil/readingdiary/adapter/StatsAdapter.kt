package com.ilsamil.readingdiary.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import java.util.*
import kotlin.collections.ArrayList

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {
    private var bookShelfList : List<String> = ArrayList()
    private val colorArr = arrayOf(R.drawable.stats_book_item, R.drawable.stats_book_item2,
                    R.drawable.stats_book_item3, R.drawable.stats_book_item4, R.drawable.stats_book_item5)
    private val random = Random()

    inner class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statsNameTv : TextView = itemView.findViewById(R.id.item_stats_name_tv)
        val statsView : View = itemView.findViewById(R.id.item_stats_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stats, parent, false)
        return StatsViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val item = bookShelfList[position]
        val colNum = random.nextInt(5)

        holder.statsNameTv.text = item
        holder.statsView.setBackgroundResource(colorArr[colNum])
//        holder.statsView.setBackgroundColor(R.color.stats_item_color1)
    }

    override fun getItemCount(): Int {
        return bookShelfList.size
    }

    fun updateItems(items : List<String>) {
        bookShelfList = items
        notifyDataSetChanged()
    }

}
