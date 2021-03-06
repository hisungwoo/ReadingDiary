package com.ilsamil.readingdiary.view

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.SearchAdapter
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.databinding.FragmentSearchBinding
import com.ilsamil.readingdiary.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var imm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.searchBackBtn.setOnClickListener { findNavController().popBackStack() }
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(
            container?.context,
            RecyclerView.VERTICAL,
            false
        )
        val adapter = SearchAdapter()
        binding.searchRecyclerView.adapter = adapter
        searchViewModel.searchItem.observe(this, Observer {
            binding.searchGuideTv.visibility = View.INVISIBLE
            binding.searchRecyclerView.visibility = View.VISIBLE
            adapter.updateItem(it)
        })

        //?????? ?????????
        adapter.setItemClickListener(object: SearchAdapter.SearchOnItemClickListener {
            override fun onClick(v: View, position: Int, item : Books) {
                val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(item)
                findNavController().navigate(action)
            }
        })

        // ????????? ?????? ?????? ???????????? ?????? ??? searchViewModel ?????? ??????
        binding.searchEt.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                val searchText = binding.searchEt.text.toString()
                if (searchText != "") {
                    val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.searchEt.windowToken, 0)
                    searchViewModel.getSearchBook(searchText)
                }
                true
            } else false
        }

        binding.searchClearBtn.setOnClickListener {
            binding.searchEt.setText("")
            focusKy()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        focusKy()
    }

    private fun focusKy() {
        imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.searchEt.requestFocus()
        imm.showSoftInput(binding.searchEt, InputMethodManager.SHOW_IMPLICIT)
    }

}