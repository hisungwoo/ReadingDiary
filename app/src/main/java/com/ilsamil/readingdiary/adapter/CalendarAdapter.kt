package com.ilsamil.readingdiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ilsamil.readingdiary.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.CalendarHeaderBinding
import com.ilsamil.readingdiary.databinding.DayBinding
import com.ilsamil.readingdiary.databinding.EmptyDayBinding
import com.ilsamil.readingdiary.databinding.FragmentHomeBinding

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
            val binding =
                DataBindingUtil.inflate<CalendarHeaderBinding>(LayoutInflater.from(parent.context), R.layout.item_calendar_header, parent, false)
            val params : StaggeredGridLayoutManager.LayoutParams = binding.root.layoutParams as StaggeredGridLayoutManager.LayoutParams
            binding.root.layoutParams = params
            return HeaderViewHolder(binding)
        } else if (viewType == EMPTY_TYPE) {
            val binding =
                DataBindingUtil.inflate<EmptyDayBinding>(LayoutInflater.from(parent.context), R.layout.item_calendar_header, parent, false)
            return EmptyViewHolder(binding)
        } else {
            val binding =
                DataBindingUtil.inflate<DayBinding>(LayoutInflater.from(parent.context), R.layout.item_calendar_header, parent, false)
            return DayViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class HeaderViewHolder(binding : CalendarHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding : CalendarHeaderBinding = binding

        private fun setViewModel(model : MainViewModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    inner class EmptyViewHolder(binding : EmptyDayBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding : EmptyDayBinding = binding

        private fun setViewModel(model : MainViewModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    inner class DayViewHolder(binding : DayBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding : DayBinding = binding

        private fun setViewModel(model : MainViewModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }
}