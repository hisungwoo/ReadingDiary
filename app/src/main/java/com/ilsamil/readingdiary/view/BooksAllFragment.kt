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
import com.ilsamil.readingdiary.databinding.FragmentBooksAllBinding
import com.ilsamil.readingdiary.utils.RecyclerDecoration
import com.ilsamil.readingdiary.viewmodel.BooksViewModel

class BooksAllFragment() : Fragment() {
    private val booksViewModel by activityViewModels<BooksViewModel>()
    private lateinit var binding : FragmentBooksAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_books_all, container, false)
        binding.booksAllRecyclerView.layoutManager = LinearLayoutManager(container?.context,
            RecyclerView.VERTICAL,
            false)
        val adapter = BooksAdapter()
        binding.booksAllRecyclerView.adapter = adapter

        val spaceDecoration = RecyclerDecoration(25)
        binding.booksAllRecyclerView.addItemDecoration(spaceDecoration)


        adapter.setBooksItemClickListener(object : BooksAdapter.BooksItemClickListener{
            override fun onClick(v: View, position: Int, item: MyBook) {
                val action = BooksFragmentDirections.actionBooksFragmentToSelBookFragment(item)
                findNavController().navigate(action)
            }
        })

        booksViewModel.apply {
            setCategoryAll()
            bookAllList.observe(this@BooksAllFragment, Observer {
                adapter.updateItems(it)
            })
        }

        return binding.root
    }
}