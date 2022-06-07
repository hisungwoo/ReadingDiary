package com.ilsamil.readingdiary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.FragmentSelBookBinding

class SelBookFragment : Fragment() {
    private lateinit var binding : FragmentSelBookBinding
    private val args by navArgs<SelBookFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val item = args.mybook
        Log.d("ttest", "들어왔음!!!!!!!!!!!!!!")
        Log.d("ttest", "책 이름 : ${item.name}")


        return inflater.inflate(R.layout.fragment_sel_book, container, false)
    }

}