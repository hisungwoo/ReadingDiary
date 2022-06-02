package com.ilsamil.readingdiary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.remote.model.Books
import com.ilsamil.readingdiary.databinding.ItemSearchResultBinding

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var sItem : List<Books> = ArrayList()
    private lateinit var sItemClickListener : SearchOnItemClickListener

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchResultBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
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
        fun setImg(iv : ImageView, books: Books) {
            iv.setImageResource(R.drawable.ic_baseline_brightness_check_1_24)
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