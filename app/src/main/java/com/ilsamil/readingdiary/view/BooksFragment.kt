package com.ilsamil.readingdiary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.BooksAdapter
import com.ilsamil.readingdiary.databinding.FragmentBooksBinding
import com.ilsamil.readingdiary.viewmodel.BooksViewModel


class BooksFragment : Fragment() {
    private val booksViewModel by activityViewModels<BooksViewModel>()
    private lateinit var binding : FragmentBooksBinding

    companion object {
        fun newInstance() : BooksFragment {
            return BooksFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_books, container, false)
        binding.booksRecyclerView.layoutManager = LinearLayoutManager(container?.context,
                RecyclerView.VERTICAL,
                false)

        val adapter = BooksAdapter()
        binding.booksRecyclerView.adapter = adapter
        val mItems = booksViewModel.getMyBooks()
        for (i in mItems.indices) {
            val curPage = booksViewModel.getCurPage(mItems[i].name)
            mItems[i].curPage = curPage
        }
        adapter.updateItems(mItems)


        binding.booksAddBtn.setOnClickListener {
            findNavController().navigate(R.id.action_booksFragment_to_searchFragment)
        }


        return binding.root
    }


}