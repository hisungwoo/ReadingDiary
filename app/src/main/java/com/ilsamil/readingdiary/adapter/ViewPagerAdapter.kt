package com.ilsamil.readingdiary.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilsamil.readingdiary.view.BooksAllFragment
import com.ilsamil.readingdiary.view.BooksFinishFragment
import com.ilsamil.readingdiary.view.BooksReadingFragment

private const val NUM_TABS = 3

class ViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BooksAllFragment()
            1 -> return BooksReadingFragment()
            2 -> return BooksFinishFragment()
        }
        return BooksAllFragment()
    }
}