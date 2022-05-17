package com.ilsamil.readingdiary.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.FragmentHomeBinding
import java.lang.Exception
import java.security.Key
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    //Category.newInstance()사용을 위해 생성
    companion object {
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
        private const val TAG = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)







        return binding?.root
    }


    private fun setCalendarList() {
        // 오늘 날짜
        val today = GregorianCalendar()

        // 모든 타입을 받는 ArrayList
        val calendarList = ArrayList<Any>()

        for (i in -300 .. 300) {
            try {
                val calendar = GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + i, 1, 0, 0, 0)

                // 날짜 타입
                calendarList.add(calendar.timeInMillis)

                // 해당 월에 시작하는 요일에 -1을 하여 빈칸 흭득
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1

                //해당 월에 마지막 요일
                val max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

                for(j in 0 .. dayOfWeek) {
                    calendarList.add(0)
                }

                for(j in 0 .. max) {
                    calendarList.add(GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                Log.d(TAG, e.toString())
            }
        }

        Log.d(TAG, calendarList.toString())
    }


}


















