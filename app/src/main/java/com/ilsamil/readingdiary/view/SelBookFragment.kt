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
import com.ilsamil.readingdiary.databinding.FragmentSelBookBinding
import com.ilsamil.readingdiary.viewmodel.SelBookViewModel

class SelBookFragment : Fragment() {
    private val selBookViewModel by activityViewModels<SelBookViewModel>()
    private lateinit var binding : FragmentSelBookBinding
    private val args by navArgs<SelBookFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sel_book, container, false)

        val item = args.mybook
        Log.d("ttest", "들어왔음!!!!!!!!!!!!!!")
        Log.d("ttest", "책 이름 : ${item.name}")

        binding.selBookName.text = item.name

        Glide.with(this)
            .load(item.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.selBookImgIv)

        binding.selBookDelBtn.setOnClickListener {
            selBookViewModel.removeBook(args.mybook)
            selBookViewModel.removeReading(args.mybook.name)
            findNavController().popBackStack()
        }



        return binding.root
    }

}