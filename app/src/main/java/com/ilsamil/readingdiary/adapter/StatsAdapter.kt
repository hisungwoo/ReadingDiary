package com.ilsamil.readingdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {
    private var bookShelfList : List<String> = ArrayList()

    inner class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statsNameTv : TextView = itemView.findViewById(R.id.item_stats_name_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stats, parent, false)
        return StatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val item = bookShelfList[position]
        holder.statsNameTv.text = item
    }

    override fun getItemCount(): Int {
        return bookShelfList.size
    }

    fun updateItems(items : List<String>) {
        bookShelfList = items
        notifyDataSetChanged()
    }
}
