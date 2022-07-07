package com.ilsamil.readingdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.databinding.ItemSearchBinding

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var sItem : List<Books> = ArrayList()
    private lateinit var sItemClickListener : SearchOnItemClickListener

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        holder.binding.books = sItem[position]

        // 클릭 이벤트
        holder.binding.itemSearchListCl.setOnClickListener {
            sItemClickListener.onClick(it, position, sItem[position])
        }
    }

    override fun getItemCount(): Int {
        return sItem.size
    }

    object SearchBindingAdapter {
        @BindingAdapter("setAuthors")
        @JvmStatic
        fun setAuthors(tv : TextView, books: Books) {
            var authors = ""
            for(i in books.authors) authors += "$i "
            tv.text = authors
        }

        @BindingAdapter("setDateTime")
        @JvmStatic
        fun setDateTime(tv : TextView, books: Books) {
            tv.text = books.datetime.substring(0,10)
        }

        @BindingAdapter("setImg")
        @JvmStatic
        fun setImg(iv : ImageView, url : String) {
            Glide.with(iv.context)
                .load(url)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_not)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv)
        }
    }

    fun updateItem(item : List<Books>) {
        sItem = item
        notifyDataSetChanged()
    }

    interface SearchOnItemClickListener {
        fun onClick(v: View, position: Int, item : Books)
    }

    fun setItemClickListener(onItemClickListener : SearchOnItemClickListener) {
        this.sItemClickListener = onItemClickListener
    }
}