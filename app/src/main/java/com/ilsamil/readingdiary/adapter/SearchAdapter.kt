package com.ilsamil.readingdiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.common.BaseViewHolder
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.databinding.ItemSearchBinding

class SearchAdapter(private val lifecycleOwner: LifecycleOwner) : ListAdapter<Books, BaseViewHolder<Books>>(SearchViewDiffCallback()) {
    var onClickItem : (Books) -> Unit = {}

//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Books> {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding, lifecycleOwner, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Books>, position: Int) {
        holder.bind(getItem(position))
    }
}

private class SearchViewHolder(private val binding: ItemSearchBinding, lifecycleOwner: LifecycleOwner, private val context: Context) : BaseViewHolder<Books>(binding.root) {

//    init {
//        binding.lifecycleOwner = lifecycleOwner
//    }

    override fun bind(item: Books) {
        binding.books = item
//        binding.itemSearchListCl.setOnClickListener { onClickItem(sItem[position]) }
//        binding.itemSearchListCl.setOnClickListener {
////            viewmodel.action.value = Event(item)
//        }
        binding.executePendingBindings()
    }
}

private class SearchViewDiffCallback : DiffUtil.ItemCallback<Books>() {
    override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
        return false
    }
}

@BindingAdapter("setAuthors")
fun setAuthors(tv : TextView, books: Books) {
    var authors = ""
    for(i in books.authors) authors += "$i "
    tv.text = authors
}

@BindingAdapter("setDateTime")
fun setDateTime(tv : TextView, books: Books) {
    tv.text = books.datetime.substring(0,10)
}

@BindingAdapter("setImg")
fun setImg(iv : ImageView, url : String) {
    Glide.with(iv.context)
        .load(url)
        .placeholder(R.drawable.img_loading)
        .error(R.drawable.img_not)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(iv)
}