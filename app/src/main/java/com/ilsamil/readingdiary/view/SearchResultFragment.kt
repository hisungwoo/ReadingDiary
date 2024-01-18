package com.ilsamil.readingdiary.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.databinding.FragmentSearchResultBinding
import com.ilsamil.readingdiary.utils.Util
import com.ilsamil.readingdiary.viewmodel.SearchResultViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class SearchResultFragment : Fragment() {
    private lateinit var binding : FragmentSearchResultBinding
    private val srViewModel by activityViewModels<SearchResultViewModel>()
    private val args by navArgs<SearchResultFragmentArgs>()
    private var maxPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        val resultItem = args.books

        var authors = ""
        for(i in resultItem.authors) authors += "$i "

        binding.apply {
            searchResultNameTv.text = args.books.title
            searchResultAuthorsTv.text = authors
            searchResultContentTv.text = args.books.contents + "···"
            searchResultPublisherTv.text = args.books.publisher
            searchResultDatetimeTv.text = args.books.datetime.substring(0,10)
            searchResultDetailBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(resultItem.url)
                startActivity(intent)
            }

            GlobalScope.launch(Dispatchers.Main) {
                val cnt = srViewModel.getPageCnt(resultItem.url)
                if (cnt == "0") {
                    searchResultPageTv.text = "알 수 없음"
                } else {
                    searchResultPageTv.text = cnt
                    maxPage = cnt.toInt()
                }
            }
        }

        Glide.with(this)
            .load(resultItem.thumbnail)
            .placeholder(R.drawable.img_loading)
            .error(R.drawable.img_not)
            .override(580,630)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.searchResultImg)

        binding.searchResultBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchResultSaveBtn.setOnClickListener {
            if (maxPage == 0) {
                AlertDialog.Builder(inflater.context)
                    .setView(R.layout.dialog_page_eidt)
                    .show()
                    .also { alertDialog ->
                        if (alertDialog == null) return@also

                        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        val pageEditTv = alertDialog.findViewById<TextView>(R.id.dialog_edit_et)
                        val saveBtn = alertDialog.findViewById<Button>(R.id.dialog_edit_save_btn)
                        val cancelBtn = alertDialog.findViewById<Button>(R.id.dialog_edit_cancel_btn)

                        saveBtn?.setOnClickListener {
                            val pageText = pageEditTv?.text.toString().trim()
                            if (pageCheck(pageText)) {
                                val editPage = pageText.toInt()
                                binding.searchResultPageTv.text = pageText
                                maxPage = editPage
                                alertDialog.dismiss()
                                binding.searchResultSaveBtn.callOnClick()

                            } else {
                                pageEditTv?.text = ""
                                Toast.makeText(inflater.context, "입력한 페이지 수를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }

                        cancelBtn?.setOnClickListener {
                            alertDialog.dismiss()
                        }
                    }

            } else {
                val util = Util()
                val addBook : () -> Unit = {
                    CoroutineScope(Dispatchers.Main).launch {
                        val check = srViewModel.checkBook(resultItem.title)
                        if (check == 0) {
                            var authors = ""
                            for(i in resultItem.authors) authors += "$i "

                            val book = MyBook(resultItem.title, resultItem.thumbnail, "", 0,  maxPage, resultItem.contents, resultItem.url, resultItem.publisher, authors)
                            srViewModel.addBooks(book)
                            findNavController().popBackStack()
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(inflater.context, "이미 서재에 존재하는 책입니다" , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                util.showDialog(inflater.context, addBook, "내 서재에 책을 추가하시겠습니까?", "추가")
            }
        }


        srViewModel.isExist.observe(viewLifecycleOwner) {
            when(it) {
                true -> {
                    var authors = ""
                    for(i in resultItem.authors) authors += "$i "

                    val book = MyBook(resultItem.title, resultItem.thumbnail, "", 0,  maxPage, resultItem.contents, resultItem.url, resultItem.publisher, authors)
                    srViewModel.addBooks(book)
                    findNavController().popBackStack()
                    findNavController().popBackStack()
                }
                false -> Toast.makeText(inflater.context, "이미 내 서재에 추가되어 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun pageCheck(page : String) : Boolean {
        try {
            val pageInt = page.toInt()
            if (pageInt < 1) return false
        } catch (e : NumberFormatException) {
            return false
        }
        return true
    }


}