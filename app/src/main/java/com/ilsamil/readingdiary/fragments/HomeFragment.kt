package com.ilsamil.readingdiary.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ilsamil.readingdiary.BR
import com.ilsamil.readingdiary.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.CalendarAdapter
import com.ilsamil.readingdiary.databinding.CalendarListBinding
import java.lang.Exception
import java.security.Key
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    lateinit var calendarAdapter : CalendarAdapter

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
//        val binding : CalendarListBinding = CalendarListBinding.inflate(inflater, container, false)
//        val binding = DataBindingUtil.inflate<FragmentHomeBinding>()
        val binding = DataBindingUtil.inflate<CalendarListBinding>(inflater, R.layout.fragment_home, container, false)
        binding.setVariable(BR.model, ViewModelProvider(this).get(MainViewModel::class.java))
        binding.lifecycleOwner = this
        binding.model = mainViewModel

//        binding.model.initCalendarList()
        binding.executePendingBindings()

        val manager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
//        calendarAdapter = CalendarAdapter()




        mainViewModel.mCalendarList.observe(this, androidx.lifecycle.Observer {
//            val view = binding.pagerClaendar
        })






        return binding?.root
    }


}


















