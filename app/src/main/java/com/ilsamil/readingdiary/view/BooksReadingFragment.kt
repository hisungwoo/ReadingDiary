package com.ilsamil.readingdiary.view

import android.os.Bundle
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
import com.ilsamil.readingdiary.databinding.FragmentBooksReadingBinding
import com.ilsamil.readingdiary.utils.RecyclerDecoration
import com.ilsamil.readingdiary.viewmodel.BooksViewModel

class BooksReadingFragment : Fragment() {
    private val booksViewModel by activityViewModels<BooksViewModel>()
    private lateinit var binding : FragmentBooksReadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_books_reading, container, false)
        binding.booksReadingRecyclerView.layoutManager = LinearLayoutManager(container?.context,
            RecyclerView.VERTICAL,
            false)
        val adapter = BooksAdapter()
        binding.booksReadingRecyclerView.adapter = adapter

        val spaceDecoration = RecyclerDecoration(25)
        binding.booksReadingRecyclerView.addItemDecoration(spaceDecoration)

        adapter.setBooksItemClickListener(object : BooksAdapter.BooksItemClickListener{
            override fun onClick(v: View, position: Int, item: MyBook) {
                val action = BooksFragmentDirections.actionBooksFragmentToSelBookFragment(item)
                findNavController().navigate(action)
            }
        })

        booksViewModel.apply {
            setCategoryReading()
            bookReadingList.observe(this@BooksReadingFragment, Observer {
                adapter.updateItems(it)
            })
        }


        return binding.root
    }

}