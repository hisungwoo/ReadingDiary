package com.ilsamil.readingdiary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.BooksAdapter
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.databinding.FragmentBooksFinishBinding
import com.ilsamil.readingdiary.utils.RecyclerDecoration
import com.ilsamil.readingdiary.viewmodel.BooksViewModel

class BooksFinishFragment : Fragment() {
    private val booksViewModel by activityViewModels<BooksViewModel>()
    private lateinit var binding : FragmentBooksFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_books_finish, container, false)
        binding.booksFinishRecyclerView.layoutManager = LinearLayoutManager(container?.context,
            RecyclerView.VERTICAL,
            false)

        val adapter = BooksAdapter().apply { bookOnClickItem = this@BooksFinishFragment::moveFinishBookItem }
        binding.booksFinishRecyclerView.adapter = adapter

        val spaceDecoration = RecyclerDecoration(25)
        binding.booksFinishRecyclerView.addItemDecoration(spaceDecoration)

        booksViewModel.apply {
            setCategoryFinish()
            bookFinishList.observe(this@BooksFinishFragment, Observer {
                adapter.updateItems(it)
            })
        }

        return binding.root
    }

    private fun moveFinishBookItem(item: MyBook) {
        val action = BooksFragmentDirections.actionBooksFragmentToSelBookFragment(item)
        findNavController().navigate(action)
    }

}