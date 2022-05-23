package com.ilsamil.readingdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.model.CalendarDay
import java.util.*

class CalendarAdapter(calendarList : ArrayList<CalendarDay>) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    var dayList : ArrayList<CalendarDay> = calendarList

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayText : TextView = itemView.findViewById(R.id.item_dayText_tv)
        var checkBtn : ImageButton = itemView.findViewById(R.id.item_check_ibtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }


    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        dayList[position].let { item ->
            if (!item.isEmpty) {
                holder.dayText.text = item.day
                holder.checkBtn.visibility = View.VISIBLE
                if(item.isRead) {
                    holder.checkBtn.setImageResource(R.drawable.ic_baseline_brightness_check_1_24)
                }
            } else {
                holder.dayText.text = ""
            }
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }


}