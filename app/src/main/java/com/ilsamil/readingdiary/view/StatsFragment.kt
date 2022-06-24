package com.ilsamil.readingdiary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.FragmentStatsBinding
import com.ilsamil.readingdiary.viewmodel.StatsViewModel

class StatsFragment : Fragment() {
    private val statsViewModel by activityViewModels<StatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatsBinding.inflate(inflater)

        statsViewModel.statsReadCnt.observe(this, Observer {
            binding.bookshelfReadingCountTv.text = it.toString() + "일"
        })

        statsViewModel.statsBookCnt.observe(this, Observer {
            binding.bookshelfReadingBooksTv.text = it.toString() + "권"
        })


        statsViewModel.setReadCnt()
        statsViewModel.setBookCnt()



        return binding.root
    }

}