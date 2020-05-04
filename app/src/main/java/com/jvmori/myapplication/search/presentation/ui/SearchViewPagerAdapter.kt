package com.jvmori.myapplication.search.presentation.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalStateException

class SearchViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> SearchPhotosFragment()
            1 -> SearchCollectionsFragment()
            2 -> SearchUsersFragment()
            else -> throw IllegalStateException()
        }
    }
}