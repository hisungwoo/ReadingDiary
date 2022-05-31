package com.ilsamil.readingdiary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.data.remote.model.Books

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var sItem : List<Books> = ArrayList()
    private lateinit var sItemClickListener : SearchOnItemClickListener

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val listCl : ConstraintLayout = itemView.findViewById(R.id.item_search_list_cl)
        var searchImg: ImageView = itemView.findViewById(R.id.item_search_img)
        var nameTv: TextView = itemView.findViewById(R.id.item_search_name_tv)
        var authorsTv: TextView = itemView.findViewById(R.id.item_search_authors_tv)
        var dateTimeTv: TextView = itemView.findViewById(R.id.item_search_datetime)
        var contentTv: TextView = itemView.findViewById(R.id.item_search_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val item = sItem[position]

        var authors = ""
        for(i in item.authors) authors += "$i "

        holder.apply {
            searchImg.setImageResource(R.drawable.ic_baseline_brightness_check_1_24)
            nameTv.text = item.title
            authorsTv.text = authors
            dateTimeTv.text = item.datetime.substring(0,10)
            contentTv.text = item.contents
        }

        // 클릭 이벤트
        holder.listCl.setOnClickListener {
            sItemClickListener.onClick(it, position, sItem[position])
        }
    }

    override fun getItemCount(): Int {
        return sItem.size
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