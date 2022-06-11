package com.ilsamil.readingdiary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.databinding.FragmentSearchResultBinding
import com.ilsamil.readingdiary.viewmodel.SearchResultViewModel

class SearchResultFragment : Fragment() {
    private lateinit var binding : FragmentSearchResultBinding
    private val srViewModel by activityViewModels<SearchResultViewModel>()
    private val args by navArgs<SearchResultFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        val resultItem = args.books

        binding.apply {
            searchResultNameTv.text = args.books.title
            searchResultAuthorsTv.text = args.books.authors.toString()
            searchResultContentTv.text = args.books.contents
            searchResultPublisherTv.text = args.books.publisher
            searchResultDatetimeTv.text = args.books.datetime.substring(0,10)
        }

        Glide.with(this)
            .load(resultItem.thumbnail)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.searchResultImg)

        binding.searchResultBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchResultSaveBtn.setOnClickListener {
            val book = MyBook(resultItem.title, resultItem.thumbnail, "", 0,  1200, resultItem.contents, resultItem.url, resultItem.publisher)
            srViewModel.addBooks(book)
        }






        return binding.root
    }




}