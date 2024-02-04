package com.ilsamil.readingdiary.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.adapter.SearchAdapter
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.databinding.FragmentSearchBinding
import com.ilsamil.readingdiary.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var imm: InputMethodManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        with(binding) {
            searchBackBtn.setOnClickListener { findNavController().popBackStack() }
            searchRecyclerView.layoutManager = LinearLayoutManager(
                container?.context,
                RecyclerView.VERTICAL,
                false
            )

            searchEt.setOnKeyListener { _, i, keyEvent ->
                if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    val searchText = searchEt.text.toString()
                    if (searchText.isNotEmpty()) {
                        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(searchEt.windowToken, 0)

                        searchViewModel.getSearchBook(searchText)
                    }
                    true
                } else false
            }

            searchClearBtn.setOnClickListener {
                searchEt.setText("")
                focusKy()
            }
        }

        val adapter = SearchAdapter().apply { onClickItem = this@SearchFragment::moveSearchResult }
        binding.searchRecyclerView.adapter = adapter
        searchViewModel.searchItem.observe(viewLifecycleOwner) {
//            binding.searchGuideTextView.visibility = View.INVISIBLE
            binding.searchRecyclerView.visibility = View.VISIBLE
            adapter.updateItem(it)
        }

        return binding.root
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

    // 클릭 이벤트
    private fun moveSearchResult(item: Books) {
        val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(item)
        findNavController().navigate(action)
    }

}