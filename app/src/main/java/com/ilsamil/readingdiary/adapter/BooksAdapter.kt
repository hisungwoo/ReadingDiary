package com.ilsamil.readingdiary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ilsamil.readingdiary.common.BaseViewHolder
import com.ilsamil.readingdiary.data.db.entity.MyBook
import com.ilsamil.readingdiary.databinding.ItemMybooksBinding
import kotlin.math.floor

class BooksAdapter(private val lifecycleOwner: LifecycleOwner) : ListAdapter<MyBook, BaseViewHolder<MyBook>>(BooksViewDiffCallback()) {
    var bookOnClickItem : (book : MyBook) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MyBook> {
        val binding = ItemMybooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MyBook>, position: Int) {
        holder.bind(getItem(position))

//        // 클릭 이벤트
//        holder.binding.itemMybooksCl.setOnClickListener {
//            bookOnClickItem(bItem[position])
//        }
    }
}

private class BooksViewHolder(private val binding: ItemMybooksBinding, lifecycleOwner: LifecycleOwner) : BaseViewHolder<MyBook>(binding.root) {
    override fun bind(item: MyBook) {
        binding.myBook = item
        binding.executePendingBindings()
    }

}

private class BooksViewDiffCallback : DiffUtil.ItemCallback<MyBook>() {
    override fun areItemsTheSame(oldItem: MyBook, newItem: MyBook): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: MyBook, newItem: MyBook): Boolean {
        return false
    }
}

@BindingAdapter("setProgress")
fun setProgress(progressBar : ProgressBar, myBook : MyBook) {
    if(myBook.curPage == 0) {
        progressBar.max = 10000
        progressBar.progress = 1
    } else {
        progressBar.max = myBook.edPage
        progressBar.progress = myBook.curPage
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setProgressText")
fun setProgressText(tv : TextView, myBook : MyBook) {
    tv.text = floor((myBook.curPage.toDouble()/myBook.edPage.toDouble())*100).toInt().toString()+ "%"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setReadingText")
fun setReadingText(tv : TextView, myBook : MyBook) {
    tv.text = "${myBook.curPage}/${myBook.edPage} 페이지"
}