package com.ilsamil.readingdiary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.adapter.StatsAdapter
import com.ilsamil.readingdiary.databinding.FragmentStatsBinding
import com.ilsamil.readingdiary.viewmodel.StatsViewModel

class StatsFragment : Fragment() {
    private val statsViewModel by activityViewModels<StatsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatsBinding.inflate(inflater)

        statsViewModel.statsReadCnt.observe(viewLifecycleOwner) {
            binding.statsReadingCountTv.text = it.toString()
        }

        statsViewModel.statsBookCnt.observe(viewLifecycleOwner) {
            binding.statsReadingBooksTv.text = it.toString()
        }

        statsViewModel.finishBook.observe(viewLifecycleOwner) {
            val adapter = StatsAdapter()
            binding.statsRecyclerView.adapter = adapter
            adapter.updateItems(it)
        }


        statsViewModel.setReadCnt()
        statsViewModel.setBookCnt()
        statsViewModel.getFinishBook()

        binding.statsRecyclerView.layoutManager = LinearLayoutManager(
            container?.context,
            RecyclerView.HORIZONTAL,
            false
        )

        return binding.root
    }

}