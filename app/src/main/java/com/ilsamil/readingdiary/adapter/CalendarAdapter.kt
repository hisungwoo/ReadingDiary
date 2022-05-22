package com.ilsamil.readingdiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ilsamil.readingdiary.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.CalendarHeaderBinding
import com.ilsamil.readingdiary.databinding.DayBinding
import com.ilsamil.readingdiary.databinding.EmptyDayBinding
import java.util.*

class CalendarAdapter(calendarList : ArrayList<String>) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    var dayList : ArrayList<String> = calendarList

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayText : TextView = itemView.findViewById(R.id.item_dayText_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }


    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayText.text = dayList[position]
    }

    override fun getItemCount(): Int {
        return dayList.size
    }


}