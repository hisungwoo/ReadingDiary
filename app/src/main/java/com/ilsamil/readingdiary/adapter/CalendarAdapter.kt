package com.ilsamil.readingdiary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ItemCalendarBinding
import com.ilsamil.readingdiary.model.CalendarDay
import java.util.*

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
//    private var dayList : ArrayList<CalendarDay> = calendarList
    private var dayList : List<CalendarDay> = ArrayList<CalendarDay>()
    private lateinit var itemClickListener : OnItemClickListener

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCalendarBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.binding.calDay = dayList[position]

        // 클릭 이벤트
        holder.binding.itemReadIbtn.setOnClickListener {
            itemClickListener.onClick(it, position, dayList[position])
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    object DataBindingAdapter {
        @BindingAdapter("readCheck")
        @JvmStatic
        fun setReadCheck(imageButton: ImageButton, calDay : CalendarDay) {
            if (!calDay.isEmpty) {
                imageButton.visibility = View.VISIBLE
                if (calDay.isRead) {
                    imageButton.setImageResource(R.drawable.ic_baseline_brightness_check_1_24)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item : CalendarDay)
    }

    fun setItemClickListener(onItemClickListener : OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun updateItem(item : List<CalendarDay>) {
        dayList = item
        notifyDataSetChanged()
    }

}