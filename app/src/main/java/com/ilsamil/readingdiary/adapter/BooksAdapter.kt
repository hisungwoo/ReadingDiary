package com.ilsamil.readingdiary.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilsamil.readingdiary.data.db.entity.MyBook

class BooksAdapter() : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private var bItem = ArrayList<MyBook>()

    inner class BooksViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}