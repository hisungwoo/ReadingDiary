package com.ilsamil.readingdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(calendarList : List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER_TYPE = 0
    private val EMPTY_TYPE = 1
    private val DAY_TYPE = 2

    private var mCalendarList = calendarList

    fun setCalendarList(calendarList : List<Any>) {
        mCalendarList = calendarList
    }

    override fun getItemViewType(position: Int): Int {
        var item = mCalendarList[position]
        return if(item is Long) {
            //날짜 타입
            HEADER_TYPE
        } else if (item is String) {
            //비어있는 일자 타입
            EMPTY_TYPE
        } else {
            DAY_TYPE
        }
//        when(item) {
//            is Long -> HEADER_TYPE
//            is String -> EMPTY_TYPE
//            else -> DAY_TYPE
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER_TYPE) {
        }
        TODO("Not yet implemented")

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class CalendarViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}