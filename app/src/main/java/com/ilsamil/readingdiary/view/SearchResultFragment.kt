package com.ilsamil.readingdiary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.FragmentSearchResultBinding

class SearchResultFragment : Fragment() {
    private lateinit var binding : FragmentSearchResultBinding
    private val args by navArgs<SearchResultFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)

        binding.apply {
            searchResultNameTv.text = args.books.title
            searchResultAuthorsTv.text = args.books.authors.toString()
            searchResultContentTv.text = args.books.contents
            searchResultPublisherTv.text = args.books.publisher
            searchResultDatetimeTv.text = args.books.datetime.substring(0,10)
        }


        binding.searchResultBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }




}