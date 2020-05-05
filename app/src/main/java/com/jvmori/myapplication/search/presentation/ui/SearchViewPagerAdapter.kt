package com.jvmori.myapplication.search.presentation.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var searchPhotosFragment: SearchPhotosFragment? = null
    var searchCollectionsFragment: SearchCollectionsFragment? = null
    var searchUsersFragment: SearchUsersFragment? = null
    var iSearchQuery: SearchFragment.ISearchQuery? = null

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchPhotosFragment().apply {
                searchPhotosFragment = this
                iSearchQuery = this
            }
            1 -> SearchCollectionsFragment().apply {
                searchCollectionsFragment = this
                iSearchQuery = this
            }
            2 -> SearchUsersFragment().apply {
                searchUsersFragment = this
                iSearchQuery = this
            }
            else -> throw IllegalStateException()
        }
    }
}