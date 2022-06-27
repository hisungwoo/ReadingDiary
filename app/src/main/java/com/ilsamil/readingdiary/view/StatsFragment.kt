package com.ilsamil.readingdiary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.StatsAdapter
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

        statsViewModel.finishBook.observe(this, Observer {
            Log.d("ttestaa", it.toString())

            val adapter = StatsAdapter()
            binding.bookshelfRecyclerView.adapter = adapter
            adapter.updateItems(it)
        })


        statsViewModel.setReadCnt()
        statsViewModel.setBookCnt()
        statsViewModel.getFinishBook()

        binding.bookshelfRecyclerView.layoutManager = LinearLayoutManager(
            container?.context,
            RecyclerView.HORIZONTAL,
            false
        )



        return binding.root
    }

}