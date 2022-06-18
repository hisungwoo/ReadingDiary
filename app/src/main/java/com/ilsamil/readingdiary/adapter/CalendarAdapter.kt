package com.ilsamil.readingdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ItemCalendarBinding
import com.ilsamil.readingdiary.data.db.entity.CalendarDay
import java.util.*

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
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
        fun setReadCheck(imgView: ImageView, calDay : CalendarDay) {
            if (!calDay.isEmpty) {
                imgView.visibility = View.VISIBLE
                if (calDay.isRead) {
                    when(calDay.day.toInt()) {
                        in 1..10 -> imgView.setImageResource(R.drawable.on2)
                        in 11..21 -> imgView.setImageResource(R.drawable.on)
                        else -> imgView.setImageResource(R.drawable.on3)
                    }
                } else {
                    imgView.setImageResource(R.drawable.off)
                }
            } else {
                imgView.visibility = View.INVISIBLE
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