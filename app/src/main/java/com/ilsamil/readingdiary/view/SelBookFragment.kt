package com.ilsamil.readingdiary.view

import android.content.Intent
import android.net.Uri
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
import com.ilsamil.readingdiary.utils.Util
import com.ilsamil.readingdiary.viewmodel.SelBookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.floor

class SelBookFragment : Fragment() {

    companion object{
        private const val TAG = "SelBookFragment_1sam1"
        var adsCnt = 0
    }

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

        binding.selBookName.text = item.name
        binding.selBookIntroduceTv.text = item.introduce + "···"
        binding.selBookProgressBar.max = item.edPage
        binding.selBookProgressBar.progress = 1
        binding.selBookPublisherTv.text = item.publisher
        binding.selBookProgressReadTv.text = "0 / ${item.edPage} 페이지"
        binding.selBookReadingAuthorsTv.text = item.authors

        GlobalScope.launch(Dispatchers.Main) {
            val stReading = selBookViewModel.getStartReading(item.name)
            val edReading = selBookViewModel.getCurReading(item.name)
            val readingDay = selBookViewModel.getReadingCnt(item.name)
            val curPage = edReading?.readEd

            if (stReading != null) {
                binding.selBookStDateTv.text = "${stReading.year}.${stReading.month}.${stReading.day}"
            }

            if (edReading != null && curPage != null) {
                binding.selBookProgressBar.progress = curPage
                binding.selBookProgressReadTv.text = "${curPage}/${item.edPage}페이지"
                binding.selBookProgressPerTv.text = floor((curPage.toDouble()/item.edPage.toDouble())*100).toInt().toString()+ "%"
                binding.selBookEdDateTv.text = "${edReading.year}.${edReading.month}.${edReading.day}"

            }

            if (readingDay != null) {
                binding.selBookReadingDayTv.text = readingDay
            }
        }

        Glide.with(this)
            .load(item.imgUrl)
            .placeholder(R.drawable.img_loading)
            .error(R.drawable.img_not)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(470,530)
            .into(binding.selBookImgIv)

        binding.selBookDelBtn.setOnClickListener {
            val util = Util()
            val removeBook : () -> Unit = {
                selBookViewModel.removeBook(args.mybook.name)
                findNavController().popBackStack()
            }

            util.showDialog(inflater.context, removeBook,"정말로 삭제 하시겠습니까?", "삭제")
        }

        binding.selBookDetailBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.contentUrl)
            startActivity(intent)
        }

        binding.selBookBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

}