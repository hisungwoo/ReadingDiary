package com.ilsamil.readingdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {
    private var bookShelfList : List<String> = ArrayList()

    inner class StatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var statsImg : ImageView = itemView.findViewById(R.id.item_stats_bookshelf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stats, parent, false)
        return StatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val item = bookShelfList[position]
        holder.statsImg.setImageResource(R.drawable.img_bookshelf)
    }

    override fun getItemCount(): Int {
        return bookShelfList.size
    }

    fun updateItems(items : List<String>) {
        bookShelfList = items
        notifyDataSetChanged()
    }
}
